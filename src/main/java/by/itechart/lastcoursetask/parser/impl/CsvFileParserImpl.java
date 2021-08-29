package by.itechart.lastcoursetask.parser.impl;

import by.itechart.lastcoursetask.dto.TransactionDto;
import by.itechart.lastcoursetask.exception.FileNotReadException;
import by.itechart.lastcoursetask.parser.api.FileParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Scope("singleton")
@Component("csv")
public class CsvFileParserImpl implements FileParser {
    private final static String STATUS_REGEX = "((success)|(failed)|(rejected))";
    private final static String UUID_REGEX = "[0-9a-z]{8}-([0-9a-z]{4}-){3}[0-9a-z]{12}";
    private final static String VALID_REGEX = "[0-9]{10,},(" + UUID_REGEX + ",){2}[0-9]+,[a-z]{3}," + STATUS_REGEX;
    private final static String TIMEZONE_OFFSET = "+02:00";
    private final static String SUCCESS_STATUS = "success";
    private final static String DELIMITER = ",";
    private final static String INVALID_DATA_MESSAGE = "Invalid data in line: ";
    private final List<String> invalidDataMessages;

    CsvFileParserImpl() {
        this.invalidDataMessages = new ArrayList<>();
    }

    @Override
    public List<TransactionDto> parse(File file) {
        log.info("Parsing CSV file");
        this.invalidDataMessages.clear();
        List<String> fileStrings = getText(file);
        List<String> validData = getValidData(fileStrings);
        return getTransactionDTOs(validData);
    }

    @Override
    public List<String> getInvalidTransactionsData() {
        return new ArrayList<>(this.invalidDataMessages);
    }

    private List<TransactionDto> getTransactionDTOs(List<String> validStrings) {
        List<TransactionDto> transactionDtos = new ArrayList<>();
        for (String data : validStrings) {
            String[] fields = data.split(DELIMITER);
            transactionDtos.add(createFromData(fields));
        }
        return transactionDtos;
    }

    private TransactionDto createFromData(String[] fields) {
        TransactionDto transactionDTO = new TransactionDto();
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

    private List<String> getValidData(List<String> text) {
        List<String> validData = new ArrayList<>();
        for (int i = 0; i < text.size(); i++) {
            if (text.get(i).matches(VALID_REGEX)) {
                validData.add(text.get(i));
            } else {
                this.invalidDataMessages.add(INVALID_DATA_MESSAGE + i);
            }
        }
        return validData;
    }

    private List<String> getText(File file) {
        List<String> fileStrings;
        try (Stream<String> stringStream = Files.lines(file.toPath())) {
            fileStrings = stringStream.collect(Collectors.toList());
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new FileNotReadException(file.getAbsolutePath());
        }
        return fileStrings;
    }
}
