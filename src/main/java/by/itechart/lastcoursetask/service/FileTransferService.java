package by.itechart.lastcoursetask.service;

import by.itechart.lastcoursetask.dto.OperatorDto;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Service which is providing the ability of upload files to the local storage
 * and parsing incoming InputStream to get inner data as list of strings.
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class FileTransferService {
    /**
     * String value of the path, which represent upload directory, should be different on macOS, Windows & Linux
     */
    @Value("${upload.path}")
    private String uploadPath;
    /**
     * TransactionService provides method saveAll{@link TransactionService#saveAll(Collection, OperatorDto)}
     * which save all valid transactions in the database
     */
    private final TransactionService transactionService;
    /**
     * With the help of OperatorService operator can be established to the uploaded transactions
     */
    private final OperatorService operatorService;
    /**
     * FileParserFactory takes parser, which correspond filename extension
     */
    private final FileParserFactory factory;

    /**
     * Parsing input data represented by multipart file with {@code FileParser}.
     * Store transaction with the given nickname by {@code OperatorService}.
     * Store uploaded file to local storage.
     * @param       files Files to parse & store
     * @param       nickname Operator nickname String value
     * @return      List of exception messages, which occurs while file have been proceeded
     * @throws      FileNotReadException
     *              If file can not be proceeded
     * @throws      by.itechart.lastcoursetask.exception.InvalidFileExtensionException
     *              If parser for the file extension is not exist
     * @throws      by.itechart.lastcoursetask.exception.TransactionExistException
     *              If transaction is already exist
     */
    public List<String> uploadFiles(MultipartFile[] files, String nickname) {
        createUploadDir();
        List<String> messages = new ArrayList<>();
        for (MultipartFile file : files) {
            messages.addAll(uploadFile(file, nickname));
        }
        return messages;
    }

    private List<String> uploadFile(MultipartFile file, String nickname) {
        FileParser parser = factory.getParser(getFilenameExtension(file.getOriginalFilename()));
        saveTransactions(file, nickname, parser);
        storeFile(file);
        return parser.getInvalidTransactionsData();
    }

    private void saveTransactions(MultipartFile file, String nickname, FileParser parser) {
        try {
            transactionService.saveAll(parser.parse(file.getInputStream(), file.getOriginalFilename()),
                    operatorService.findByNickName(nickname));
        } catch (IOException e) {
            log.error(e.getMessage(), e.getLocalizedMessage());
            throw new FileNotReadException("File have not been read: " + file.getOriginalFilename());
        }
    }

    private void createUploadDir() {
        File file = new File(uploadPath);
        if (Files.notExists(Paths.get(uploadPath))) {
            log.info("Creating upload dir");
            boolean result = file.mkdirs();
            if (!result) {
                throw new FileNotReadException("Upload directory is not found");
            }
        }
    }

    private void storeFile(MultipartFile file) {
        String uploadFilename = UUID.randomUUID() + "." + file.getOriginalFilename();
        try {
            Path path = Paths.get(uploadPath).resolve(uploadFilename);
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new FileNotReadException("Can not store file: " + file.getOriginalFilename());
        }
    }

    private String getFilenameExtension(String filename) {
        int pos = filename.lastIndexOf('.');
        return filename.substring(pos + 1);
    }
}

/**
 * @Author      Vanya Zelezinsky
 * @Version     1.0
 * @see         by.itechart.lastcoursetask.service.TransactionService
 * @see         by.itechart.lastcoursetask.parser.api.FileParser
 * @see         by.itechart.lastcoursetask.service.OperatorService
 */
