package by.itechart.lastcoursetask.parser.api;

import by.itechart.lastcoursetask.dto.TransactionDTO;

import java.io.File;
import java.util.List;

public interface FileParser {
    List<TransactionDTO> parse(File file);
}
