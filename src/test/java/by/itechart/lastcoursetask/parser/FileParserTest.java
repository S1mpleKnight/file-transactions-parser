package by.itechart.lastcoursetask.parser;

import by.itechart.lastcoursetask.dto.TransactionDTO;
import by.itechart.lastcoursetask.parser.api.FileParser;
import by.itechart.lastcoursetask.parser.impl.CSVFileParserImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:test.properties")
class FileParserTest {

    @Test
    void parseCSV() {
        File file = new File("C:\\Users\\Becom\\Downloads\\csv_example.csv");
        FileParser fileParser = CSVFileParserImpl.getInstance();
        List<TransactionDTO> transactionDTOs = fileParser.parse(file);
        System.out.println(transactionDTOs);
        assertNotEquals(0, transactionDTOs.size());
    }
}