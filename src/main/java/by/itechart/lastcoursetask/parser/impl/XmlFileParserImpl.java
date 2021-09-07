package by.itechart.lastcoursetask.parser.impl;

import by.itechart.lastcoursetask.dto.TransactionDto;
import by.itechart.lastcoursetask.parser.api.FileParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component("xml")
@Scope("singleton")
public class XmlFileParserImpl implements FileParser {
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final static String SUCCESS_TRANSACTION_STATUS = "COMPLETE";
    private static final String TRANSACTION_TAG = "transaction";
    private static final String USER_TAG = "user";
    private static final String ID_TAG = "id";
    private static final String[] FIRST_LEVEL_TAGS = {"timestamp", "status", ID_TAG};
    private static final String[] PAYMENT_DETAILS_TAGS = {"amount", "currency"};
    private static final String DATE_TIME_REGEX = "[0-9]{4}(-[0-9]{2}){2} ([0-9]{2}:){2}[0-9]{2}";
    private static final String STATUS_REGEX = "((COMPLETE)|(FAILURE))";
    private static final String ID_REGEX = "[0-9a-z]{8}-([0-9a-z]{4}-){3}[0-9a-z]{12}";
    private static final String AMOUNT_REGEX = "[0-9 ]+";
    private static final String CURRENCY_REGEX = "[A-Z]{3}";
    private final List<String> invalidDataMessages;
    private TransactionDto transactionDto;

    XmlFileParserImpl() {
        this.invalidDataMessages = new ArrayList<>();
    }

    @Override
    public List<TransactionDto> parse(InputStream stream, String filename) {
        log.info("Parsing XML file");
        this.invalidDataMessages.clear();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        return getTransactions(stream, filename, factory);
    }

    private List<TransactionDto> getTransactions(InputStream stream, String filename, DocumentBuilderFactory factory) {
        try {
            NodeList list = getNodeList(stream, factory);
            return getTransactions(list, filename);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    private NodeList getNodeList(InputStream stream, DocumentBuilderFactory factory) throws ParserConfigurationException,
            SAXException, IOException {
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(stream);
        return document.getElementsByTagName(TRANSACTION_TAG);
    }

    private List<TransactionDto> getTransactions(NodeList list, String filename) {
        List<TransactionDto> transactions = new ArrayList<>();
        for (int i = 0; i < list.getLength(); i++) {
            getDataFromTransaction((Element) list.item(i), transactions, i, filename);
        }
        return transactions;
    }

    private void getDataFromTransaction(Element element, List<TransactionDto> transactions, int transactionPosition,
                                        String filename) {
        this.transactionDto = new TransactionDto();
        if (handleElement(element)) {
            transactions.add(transactionDto);
        } else {
            this.invalidDataMessages.add("Invalid data in transaction: #" + (transactionPosition + 1)
                    + ", in file: " + filename);
        }
    }

    private boolean handleElement(Element element) {
        return handleMultipleNodes(element, FIRST_LEVEL_TAGS) && handleSecondLevelChildren(element);
    }

    private boolean handleSecondLevelChildren(Element element) {
        return setCustomerIdValue(element) && handleMultipleNodes(element, PAYMENT_DETAILS_TAGS);
    }

    private boolean setCustomerIdValue(Element element) {
        String textContext = getCustomerIdContext(element);
        return textContext.matches(getCorrespondRegex(ID_TAG)) && setCustomerId(textContext);
    }

    private boolean setCustomerId(String textContext) {
        this.transactionDto.setCustomerId(textContext);
        return true;
    }

    private String getCustomerIdContext(Element element) {
        Node node = element.getElementsByTagName(USER_TAG).item(0);
        return getTextContent((Element) node, ID_TAG);
    }

    private boolean handleMultipleNodes(Element element, String[] tags) {
        for (String tag : tags) {
            String textContext = getTextContent(element, tag);
            if (textContext.matches(getCorrespondRegex(tag))) {
                setCorrespondValue(tag, textContext);
            } else {
                return false;
            }
        }
        return true;
    }

    private String getTextContent(Element element, String tag) {
        return element.getElementsByTagName(tag).item(0).getTextContent();
    }

    private void setCorrespondValue(String tag, String fieldValue) {
        switch (tag) {
            case "status" -> this.transactionDto.setStatus(fieldValue.equals(SUCCESS_TRANSACTION_STATUS));
            case "timestamp" -> this.transactionDto.setDateTime(LocalDateTime.parse(fieldValue, format));
            case "currency" -> this.transactionDto.setCurrency(fieldValue);
            case "amount" -> this.transactionDto.setAmount(fieldValue);
            case "id" -> this.transactionDto.setTransactionId(fieldValue);
        }
    }

    private String getCorrespondRegex(String tag) {
        return switch (tag) {
            case "timestamp" -> DATE_TIME_REGEX;
            case "status" -> STATUS_REGEX;
            case "id" -> ID_REGEX;
            case "amount" -> AMOUNT_REGEX;
            case "currency" -> CURRENCY_REGEX;
            default -> throw new IllegalArgumentException("Illegal tag name");
        };
    }

    @Override
    public List<String> getInvalidTransactionsData() {
        return new ArrayList<>(invalidDataMessages);
    }
}
