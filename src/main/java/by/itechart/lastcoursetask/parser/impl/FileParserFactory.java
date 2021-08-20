package by.itechart.lastcoursetask.parser.impl;

import by.itechart.lastcoursetask.exception.InvalidFileExtensionException;
import by.itechart.lastcoursetask.parser.api.FileParser;
import org.springframework.stereotype.Component;

@Component
public class FileParserFactory {
    private final static String CSV_EXTENSION = "csv";
    private final static String XML_EXTENSION = "xml";

    public static FileParser getParser(String filenameExtension) {
        if (filenameExtension.equals(CSV_EXTENSION)) {
            return new CsvFileParserImpl();
        }
        if (filenameExtension.equals(XML_EXTENSION)) {
            return new XmlFileParserDomImpl();
        }
        throw new InvalidFileExtensionException(filenameExtension);
    }
}
