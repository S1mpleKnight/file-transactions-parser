package by.itechart.lastcoursetask.parser.api;

import by.itechart.lastcoursetask.dto.TransactionDto;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public interface FileParser {
    List<TransactionDto> parse(InputStream stream);

    List<String> getInvalidTransactionsData();
}
