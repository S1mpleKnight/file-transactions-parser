package by.itechart.lastcoursetask.parser.api;

import by.itechart.lastcoursetask.dto.TransactionDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FileParser {
    List<TransactionDto> parse(Object data);

    List<String> getInvalidTransactionsData();
}
