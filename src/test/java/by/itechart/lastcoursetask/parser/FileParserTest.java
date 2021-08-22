package by.itechart.lastcoursetask.parser;

import by.itechart.lastcoursetask.dto.TransactionDto;
import by.itechart.lastcoursetask.parser.api.FileParser;
import by.itechart.lastcoursetask.parser.impl.FileParserFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:test.properties")
class FileParserTest {
    private static final String CSV_FILE_PATH = ".\\examples\\csv_example.csv";
    private static final String XML_FILE_PATH = ".\\examples\\xml_example.xml";
    @Autowired
    private FileParserFactory factory;

    @ParameterizedTest(name = "{index} {0} is parsed successfully")
    @ValueSource(strings = {XML_FILE_PATH, CSV_FILE_PATH})
    void parseFileSuccessfully(String value) {
        File file = new File(value);
        FileParser fileParser = factory.getParser(getFilenameExtension(file));
        List<TransactionDto> transactionDtos = fileParser.parse(file);
        System.out.println(transactionDtos);
        System.out.println(fileParser.getInvalidTransactionsData());
        assertNotEquals(0, transactionDtos.size());
    }

    private String getFilenameExtension(File file) {
        String filename = file.getName();
        int pos = filename.lastIndexOf('.');
        return filename.substring(pos + 1);
    }
}