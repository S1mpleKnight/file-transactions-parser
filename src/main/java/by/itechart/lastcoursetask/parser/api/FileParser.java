package by.itechart.lastcoursetask.parser.api;

import by.itechart.lastcoursetask.dto.TransactionDTO;

import java.io.File;

public interface FileParser {
    TransactionDTO parse(File file);
}
