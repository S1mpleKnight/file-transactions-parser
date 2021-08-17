package by.itechart.lastcoursetask.parser.api;

import by.itechart.lastcoursetask.dto.TransactionDTO;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public interface FileParser {
    List<TransactionDTO> parse(File file);
}
