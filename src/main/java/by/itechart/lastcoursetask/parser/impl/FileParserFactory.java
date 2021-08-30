package by.itechart.lastcoursetask.parser.impl;

import by.itechart.lastcoursetask.exception.InvalidFileExtensionException;
import by.itechart.lastcoursetask.parser.api.FileParser;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Data
@Scope("singleton")
public class FileParserFactory {
    private final Map<String, FileParser> parserMap;

    public FileParser getParser(String filenameExtension) {
        if (parserMap.containsKey(filenameExtension)) {
            return parserMap.get(filenameExtension);
        }
        throw new InvalidFileExtensionException(filenameExtension);
    }
}
