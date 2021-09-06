package by.itechart.lastcoursetask.parser.impl;

import by.itechart.lastcoursetask.dto.TransactionDto;
import by.itechart.lastcoursetask.parser.api.FileParser;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@ActiveProfiles("test")
class XmlFileParserImplTest {
    private static final String XML_FILE_PATH = ".\\examples\\xml_example.xml";
    @Autowired
    private FileParserFactory factory;

    @Test
    void parse() throws FileNotFoundException {
        File file = new File(XML_FILE_PATH);
        FileParser parser = factory.getParser(getFilenameExtension(file));
        List<TransactionDto> transactions = parser.parse(new FileInputStream(file), file.getName());
        System.out.println(transactions);
        System.out.println(parser.getInvalidTransactionsData());
        assertNotEquals(0, transactions.size());
    }

    private String getFilenameExtension(File file) {
        String filename = file.getName();
        int pos = filename.lastIndexOf('.');
        return filename.substring(pos + 1);
    }
}