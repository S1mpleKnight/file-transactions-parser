package by.itechart.lastcoursetask.service;

import by.itechart.lastcoursetask.dto.TransactionDto;
import by.itechart.lastcoursetask.exception.FileNotReadException;
import by.itechart.lastcoursetask.parser.api.FileParser;
import by.itechart.lastcoursetask.parser.impl.FileParserFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class FileTransferService {
    private final static String UPLOAD_PATH = "./uploads";
    private final TransactionService transactionService;
    private final OperatorService operatorService;
    private final FileParserFactory factory;

    public List<String> uploadFile(MultipartFile file, Long id) {
        File loadedFile = storeFile(file);
        FileParser parser = factory.getParser(getFilenameExtension(loadedFile));
        List<TransactionDto> transactions = parser.parse(loadedFile);
        transactionService.saveAll(transactions, operatorService.findById(id));
        return parser.getInvalidTransactionsData();
    }

    private File storeFile(MultipartFile file) {
        String anotherFilename = UPLOAD_PATH + "/" + UUID.randomUUID() + "." + file.getOriginalFilename();
        File newFile = new File(anotherFilename);
        try {
            file.transferTo(newFile);
            return newFile;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new FileNotReadException(file.getOriginalFilename());
        }
    }

    private String getFilenameExtension(File file) {
        String filename = file.getName();
        int pos = filename.lastIndexOf('.');
        return filename.substring(pos + 1);
    }
}
