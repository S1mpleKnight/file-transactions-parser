package by.itechart.lastcoursetask.service;

import by.itechart.lastcoursetask.exception.FileNotReadException;
import by.itechart.lastcoursetask.parser.api.FileParser;
import by.itechart.lastcoursetask.parser.impl.FileParserFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileTransferService {
    @Value("${upload.path}")
    private String uploadPath;
    private final TransactionService transactionService;
    private final OperatorService operatorService;
    private final FileParserFactory factory;

    public List<String> uploadFile(MultipartFile file, String nickname) {
        createUploadDir();
        File loadedFile = storeFile(file);
        FileParser parser = factory.getParser(getFilenameExtension(loadedFile.getAbsolutePath()));
        transactionService.saveAll(parser.parse(loadedFile), operatorService.findByNickName(nickname));
        return parser.getInvalidTransactionsData();
    }

    private void createUploadDir() {
        File file = new File(uploadPath);
        if (!file.exists()) {
            log.info("Creating upload dir");
            boolean result = file.mkdirs();
            if (!result) {
                throw new FileNotReadException("Upload directory is not found");
            }
        }
    }

    private File storeFile(MultipartFile file) {
        String anotherFilename = uploadPath + "/" + UUID.randomUUID() + "." + file.getOriginalFilename();
        File newFile = new File(anotherFilename);
        try {
            file.transferTo(newFile);
            return newFile;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new FileNotReadException(file.getOriginalFilename());
        }
    }

    private String getFilenameExtension(String filename) {
        int pos = filename.lastIndexOf('.');
        return filename.substring(pos + 1);
    }
}
