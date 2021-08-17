package by.itechart.lastcoursetask.parser.impl;

import by.itechart.lastcoursetask.dto.TransactionDTO;
import by.itechart.lastcoursetask.exception.FileNotReadException;
import by.itechart.lastcoursetask.parser.api.FileParser;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CSVFileParserImpl implements FileParser {
    private final static String VALID_REGEX = "[0-9]{10,},([0-9a-z-]{36},){2}[0-9]+,[a-z]{3},[a-z]{6,8}";
    private final static String TIMEZONE_OFFSET = "+02:00";
    private final static String SUCCESS_STATUS = "success";
    private final static String DELIMITER = ",";
    private static CSVFileParserImpl parser;

    private CSVFileParserImpl() {
    }

    public static CSVFileParserImpl getInstance() {
        if (parser == null) {
            parser = new CSVFileParserImpl();
        }
        return parser;
    }

    @Override
    public List<TransactionDTO> parse(File file) {
        List<String> fileStrings = getText(file);
        List<String> validStrings = getValidStrings(fileStrings);
        return getTransactionDTOs(validStrings);
    }

    private List<TransactionDTO> getTransactionDTOs(List<String> validStrings) {
        List<TransactionDTO> transactionDTOs = new ArrayList<>();
        for (String data : validStrings) {
            String[] fields = data.split(DELIMITER);
            transactionDTOs.add(createFromData(fields));
        }
        return transactionDTOs;
    }

    private TransactionDTO createFromData(String[] fields) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setDateTime(getDateTime(fields[0]));
        transactionDTO.setTransactionId(fields[1]);
        transactionDTO.setCustomerId(fields[2]);
        transactionDTO.setAmount(fields[3]);
        transactionDTO.setCurrency(fields[4]);
        transactionDTO.setStatus(getStatus(fields[5]));
        return transactionDTO;
    }

    private boolean getStatus(String status) {
        return status.equals(SUCCESS_STATUS);
    }

    private String getDateTime(String field) {
        long time = Long.parseLong(field);
        return LocalDateTime.ofEpochSecond(time, 0, ZoneOffset.of(TIMEZONE_OFFSET)).toString();
    }

    private List<String> getValidStrings(List<String> text) {
        return text.stream()
                .filter(line -> line.matches(VALID_REGEX))
                .collect(Collectors.toList());
    }

    private List<String> getText(File file) {
        List<String> fileStrings;
        try (Stream<String> stringStream = Files.lines(file.toPath())) {
            fileStrings = stringStream.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();//todo: add some logs
            throw new FileNotReadException(file.getAbsolutePath());
        }
        return fileStrings;
    }
}
