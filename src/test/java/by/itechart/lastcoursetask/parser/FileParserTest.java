package by.itechart.lastcoursetask.parser;

import by.itechart.lastcoursetask.dto.TransactionDTO;
import by.itechart.lastcoursetask.parser.api.FileParser;
import by.itechart.lastcoursetask.parser.impl.CSVFileParserImpl;
import by.itechart.lastcoursetask.parser.impl.XMLFileParserImpl;
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


    @Test
    void parseCSVSuccess() {
        File file = new File(CSV_FILE_PATH);
        FileParser fileParser = CSVFileParserImpl.getInstance();
        List<TransactionDTO> transactionDTOs = fileParser.parse(file);
        System.out.println(transactionDTOs);
        assertNotEquals(0, transactionDTOs.size());
    }

    @Test
    void parseXMLSuccess() {
        File file = new File(XML_FILE_PATH);
        FileParser fileParser = XMLFileParserImpl.getInstance();
        List<TransactionDTO> transactionDTOs = fileParser.parse(file);
        System.out.println(transactionDTOs);
        assertNotEquals(0, transactionDTOs.size());
    }
}