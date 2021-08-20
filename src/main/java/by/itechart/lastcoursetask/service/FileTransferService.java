package by.itechart.lastcoursetask.service;

import by.itechart.lastcoursetask.dto.TransactionDto;
import by.itechart.lastcoursetask.exception.FileNotReadException;
import by.itechart.lastcoursetask.exception.InvalidFileExtensionException;
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
    private final static String CSV_EXTENSION = "csv";
    private final static String XML_EXTENSION = "xml";
    private final TransactionService transactionService;
    private final OperatorService operatorService;

    public void loadFile(MultipartFile file, Long id) {
        File loadedFile = storeFile(file);
        String filenameExtension = checkCSVExtension(getFilenameExtension(loadedFile));
        if (filenameExtension != null) {
            List<TransactionDto> transactions = readTransactions(loadedFile, filenameExtension);
            transactionService.saveAll(transactions, operatorService.findById(id));
        } else {
            throw new InvalidFileExtensionException(file.getOriginalFilename());
        }
    }

    private List<TransactionDto> readTransactions(File loadedFile, String filenameExtension) {
        FileParser parser = FileParserFactory.getParser(filenameExtension);
        return parser.parse(loadedFile);
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
        return filename.substring(pos);
    }

    private String checkCSVExtension(String filenameExtension) {
        return isCSV(filenameExtension) ? CSV_EXTENSION : checkXMLExtension(filenameExtension);
    }

    private String checkXMLExtension(String filenameExtension) {
        return isXML(filenameExtension) ? XML_EXTENSION : null;
    }

    private boolean isCSV(String filenameExtension) {
        return filenameExtension.equals(CSV_EXTENSION);
    }


    private boolean isXML(String filenameExtension) {
        return filenameExtension.equals(XML_EXTENSION);
    }

}
