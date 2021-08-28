package by.itechart.lastcoursetask.parser.api;

import by.itechart.lastcoursetask.dto.TransactionDto;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Component
public interface FileParser {
    List<TransactionDto> parse(MultipartFile file);

    List<TransactionDto> parse(File file);

    List<String> getInvalidTransactionsData();
}
