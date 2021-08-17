package by.itechart.lastcoursetask.parser.impl;

import by.itechart.lastcoursetask.dto.TransactionDTO;
import by.itechart.lastcoursetask.exception.FileNotReadException;
import by.itechart.lastcoursetask.handler.SAXHandler;
import by.itechart.lastcoursetask.parser.api.FileParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class XMLFileParserImpl implements FileParser {
    private static XMLFileParserImpl parser;

    private XMLFileParserImpl() {
    }

    public static XMLFileParserImpl getInstance() {
        if (parser == null) {
            parser = new XMLFileParserImpl();
        }
        return parser;
    }

    @Override
    public List<TransactionDTO> parse(File file) {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXHandler handler = new SAXHandler();
        return getTransactionDTOs(file, spf, handler);
    }

    private List<TransactionDTO> getTransactionDTOs(File file, SAXParserFactory spf, SAXHandler handler) {
        try {
            SAXParser saxParser = spf.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(handler);
            xmlReader.parse(file.getAbsolutePath());
            return handler.getTransactionsList();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            log.error(e.getMessage());
            throw new FileNotReadException(file.getAbsolutePath());
        }
    }
}
