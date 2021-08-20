package by.itechart.lastcoursetask.parser;

import by.itechart.lastcoursetask.dto.TransactionDTO;
import by.itechart.lastcoursetask.parser.api.FileParser;
import by.itechart.lastcoursetask.parser.impl.FileParserFactory;
import org.junit.jupiter.api.Test;
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
    private static final String CSV_FILE_PATH = "C:\\Users\\Becom\\Downloads\\csv_example.csv";
    private static final String XML_FILE_PATH = "C:\\Users\\Becom\\Downloads\\xml_example.xml";
    private final static String CSV_EXTENSION = "csv";
    private final static String XML_EXTENSION = "xml";

    @Test
    void parseCSVSuccess() {
        File file = new File(CSV_FILE_PATH);
        FileParser fileParser = FileParserFactory.getParser(CSV_EXTENSION);
        List<TransactionDTO> transactionDTOs = fileParser.parse(file);
        System.out.println(transactionDTOs);
        System.out.println(fileParser.getInvalidTransactionsData());
        assertNotEquals(0, transactionDTOs.size());
    }

    @Test
    void parseXMLSuccess() {
        File file = new File(XML_FILE_PATH);
        FileParser fileParser = FileParserFactory.getParser(XML_EXTENSION);
        List<TransactionDTO> transactionDTOs = fileParser.parse(file);
        System.out.println(transactionDTOs);
        System.out.println(fileParser.getInvalidTransactionsData());
        assertNotEquals(0, transactionDTOs.size());
    }
}