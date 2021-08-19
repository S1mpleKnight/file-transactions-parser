package by.itechart.lastcoursetask.controller;

import by.itechart.lastcoursetask.dto.TransactionDTO;
import by.itechart.lastcoursetask.exception.FileNotReadException;
import by.itechart.lastcoursetask.exception.InvalidFileExtensionException;
import by.itechart.lastcoursetask.parser.api.FileParser;
import by.itechart.lastcoursetask.parser.impl.FileParserFactory;
import by.itechart.lastcoursetask.service.OperatorService;
import by.itechart.lastcoursetask.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionsController {
    private final static String CSV_EXTENSION = "csv";
    private final static String XML_EXTENSION = "xml";
    private final static String UPLOAD_PATH = "./uploads";
    private final TransactionService transactionService;
    private final OperatorService operatorService;

    @GetMapping
    public Set<TransactionDTO> findAll() {
        log.info("Find All");
        return transactionService.findAll();
    }

    @GetMapping("/{id}")
    public TransactionDTO findById(@PathVariable String id) {
        log.info("Find by id: " + id);
        return transactionService.findById(UUID.fromString(id));
    }

    @GetMapping("/customer/{id}")
    public Set<TransactionDTO> findByCustomerId(@PathVariable String id) {
        log.info("Find by customer id: " + id);
        return transactionService.findByCustomerId(UUID.fromString(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        log.info("Delete id: " + id);
        transactionService.delete(UUID.fromString(id));
    }

    @PutMapping("/{id}")
    public void update(@PathVariable String id, @RequestParam TransactionDTO transactionDTO) {
        log.info("Update id: " + id);
        transactionService.update(UUID.fromString(id), transactionDTO);
    }

    //todo: get operator object from Spring Security
    @PostMapping
    public void save(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) {
        log.info("Save file: " + file.getOriginalFilename() + ", operator: " + id);
        File loadedFile = loadFile(file);
        String filenameExtension = checkCSVExtension(getFilenameExtension(loadedFile));
        if (filenameExtension != null) {
            List<TransactionDTO> transactions = readTransactions(loadedFile, filenameExtension);
            transactionService.saveAll(transactions, operatorService.findById(id));
        } else {
            throw new InvalidFileExtensionException(file.getOriginalFilename());
        }
    }

    private List<TransactionDTO> readTransactions(File loadedFile, String filenameExtension) {
        FileParser parser = FileParserFactory.getParser(filenameExtension);
        return parser.parse(loadedFile);
    }

    private File loadFile(MultipartFile file) {
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
