package by.itechart.lastcoursetask.parser.impl;

import by.itechart.lastcoursetask.dto.TransactionDto;
import by.itechart.lastcoursetask.parser.api.FileParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<TransactionDto> parse(InputStream stream) {
        log.info("Parsing CSV file");
        this.invalidDataMessages.clear();
        BufferedReader reader = getBufferedReader(stream);
        List<String> validData = getValidDataFromReader(reader);
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

    private LocalDateTime getDateTime(String field) {
        long time = Long.parseLong(field);
        return LocalDateTime.ofEpochSecond(time, 0, ZoneOffset.of(TIMEZONE_OFFSET));
    }

    private List<String> getValidDataLines(List<String> text) {
        List<String> validData = new ArrayList<>();
        for (int i = 0; i < text.size(); i++) {
            if (text.get(i).matches(VALID_REGEX)) {
                validData.add(text.get(i));
            } else {
                this.invalidDataMessages.add(INVALID_DATA_MESSAGE + (i + 1));
            }
        }
        return validData;
    }

    private List<String> getValidDataFromReader(BufferedReader reader) {
        readColumnNaming(reader);
        List<String> text = reader.lines().collect(Collectors.toList());
        return getValidDataLines(text);
    }

    private void readColumnNaming(BufferedReader reader) {
        try {
            checkColumnsNaming(reader);
        } catch (IOException e) {
            log.error(e.getMessage());
            invalidDataMessages.add("Columns naming have missed");
        }
    }

    private void checkColumnsNaming(BufferedReader reader) throws IOException {
        String naming = reader.readLine();
        if (!naming.matches(".*(" + DELIMITER + ".*){5}")) {
            invalidDataMessages.add("Columns naming have missed");
        }
    }

    private BufferedReader getBufferedReader(InputStream stream) {
        return new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
    }
}
