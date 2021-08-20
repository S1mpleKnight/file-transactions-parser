package by.itechart.lastcoursetask.parser.impl;

import by.itechart.lastcoursetask.dto.TransactionDto;
import by.itechart.lastcoursetask.exception.FileNotReadException;
import by.itechart.lastcoursetask.handler.SaxHandler;
import by.itechart.lastcoursetask.parser.api.FileParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
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
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component
public class XmlFileParserSaxImpl implements FileParser {
    XmlFileParserSaxImpl() {
    }

    @Override
    public List<TransactionDto> parse(File file) {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SaxHandler handler = new SaxHandler();
        return getTransactionDTOs(file, spf, handler);
    }

    @Override
    public List<String> getInvalidTransactionsData() {
        return null;
    }

    private List<TransactionDto> getTransactionDTOs(File file, SAXParserFactory spf, SaxHandler handler) {
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
