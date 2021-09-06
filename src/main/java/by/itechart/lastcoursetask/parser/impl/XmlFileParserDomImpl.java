package by.itechart.lastcoursetask.parser.impl;

import by.itechart.lastcoursetask.dto.TransactionDto;
import by.itechart.lastcoursetask.parser.api.FileParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
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

/**
 * Implementation of FileParser which works with XML files
 *
 * @since      1.0
 * @author      Vanya Zelezinsky
 * @see         by.itechart.lastcoursetask.parser.impl.CsvFileParserImpl
 * @see         by.itechart.lastcoursetask.parser.api.FileParser
 * @see         by.itechart.lastcoursetask.parser.impl.FileParserFactory
 */

@Slf4j
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
//@Component("xml")
public class XmlFileParserDomImpl implements FileParser {
    private final static String SUCCESS_TRANSACTION_STATUS = "COMPLETE";
    /**
     * Regular expression for the XML file structure validation
     */
    private final static String DATE_TIME_REGEX = "[0-9]{4}(-[0-9]{2}){2} ([0-9]{2}:){2}[0-9]{2}";
    private final static String STATUS_REGEX = "((complete)|(failure))";
    private final static String CURRENCY_REGEX = "[A-Z]{3}";
    private final static String AMOUNT_REGEX = "[0-9 ]+";
    private final static String UUID_REGEX = "[0-9a-z]{8}-([0-9a-z]{4}-){3}[0-9a-z]{12}";
    private final static String TRANSACTION_TAG_NAME = "transaction";
    /**
     * Positions of nodes in their parents
     */
    private final static int ID_NODE_AMOUNT = 1;
    private final static int ID_TAG_POS = 0;
    private final static int CUSTOMER_ID_TAG_POS = 1;
    private final static int USER_TAG_POS = 1;
    private final static int DATE_TIME_TAG_POS = 2;
    private final static int PAYMENT_DETAILS_TAG_POS = 3;
    private final static int AMOUNT_TAG_POS = 0;
    private final static int CURRENCY_TAG_POS = 1;
    private final static int STATUS_TAG_POS = 4;
    /**
     * Storage for all exception messages, if they were occurred
     */
    private final List<String> invalidDataMessages;
    /**
     * Temporary storage for the proceeding transaction
     */
    private TransactionDto transactionDTO;

    XmlFileParserDomImpl() {
        this.invalidDataMessages = new ArrayList<>();
    }

    /**
     * Takes list of exceptions
     * @return      List of exception, which occurs while parsing incoming data
     */
    @Override
    public List<String> getInvalidTransactionsData() {
        return new ArrayList<>(this.invalidDataMessages);
    }

    /**
     * Parsing incoming data from XML file to the {@code TransactionDto} list
     * @param       stream Representation of given data
     * @return      List of successfully parsed {@code TransactionDto} objects
     * @see         TransactionDto
     */
    @Override
    public List<TransactionDto> parse(InputStream stream, String filename) {
        log.info("Parsing XML file");
        this.invalidDataMessages.clear();
        DocumentBuilderFactory factory = prepareParser();
        return getTransactionsDto(stream, factory, filename);
    }

    private List<TransactionDto> getTransactionsDto(InputStream stream, DocumentBuilderFactory factory, String filename) {
        try {
            Document document = getStreamDocument(stream, factory);
            NodeList nodeList = document.getElementsByTagName(TRANSACTION_TAG_NAME);
            return getTransactions(nodeList, filename);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    private Document getStreamDocument(InputStream stream, DocumentBuilderFactory factory)
            throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(stream);
    }

    private DocumentBuilderFactory prepareParser() {
        this.invalidDataMessages.clear();
        return DocumentBuilderFactory.newInstance();
    }

    private List<TransactionDto> getTransactions(NodeList nodeList, String filename) {
        List<TransactionDto> transactions = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            this.transactionDTO = new TransactionDto();
            addTransaction(transactions, node, i, filename);
        }
        return transactions;
    }

    private void addTransaction(List<TransactionDto> transactions, Node node, Integer transactionPosition, String filename) {
            if (fillTransaction(node)) {
                transactions.add(this.transactionDTO);
            } else {
                this.invalidDataMessages.add("Invalid data in transaction: #" + (transactionPosition + 1)
                        + " file: " + filename);
            }
    }

    private boolean fillTransaction(Node node) {
        List<Node> nodes = deleteWhitespaces(node);
        return checkIdAmount(nodes) && setTransactionId(nodes) && setCustomerId(nodes) && setDateTime(nodes) && setCurrency(nodes)
            && setAmount(nodes) && setStatus(nodes);
    }

    private boolean checkIdAmount(List<Node> nodes) {
        return isTransactionIdExist(nodes) && isCustomerIdExist(nodes);
    }

    private boolean isCustomerIdExist(List<Node> nodes) {
        return nodes.get(USER_TAG_POS).hasChildNodes()
                && deleteWhitespaces(nodes.get(USER_TAG_POS)).get(ID_TAG_POS).getNodeName().equals("id");
    }

    private boolean isTransactionIdExist(List<Node> nodes) {
        return nodes.stream().filter(node -> node.getNodeName().equals("id")).count() == ID_NODE_AMOUNT;
    }

    private List<Node> deleteWhitespaces(Node parentNode) {
        NodeList children = parentNode.getChildNodes();
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).hasChildNodes()) {
                nodes.add(children.item(i));
            }
        }
        return nodes;
    }

    private boolean setStatus(List<Node> nodes) {
        String statusValue = getStringValue(nodes, STATUS_TAG_POS);
        if (statusValue.toLowerCase().matches(STATUS_REGEX)) {
            this.transactionDTO.setStatus(statusValue.equals(SUCCESS_TRANSACTION_STATUS));
            return true;
        }
        return false;
    }
    
    private boolean setCurrency(List<Node> nodes) {
        String currencyValue = getStringValue(deleteWhitespaces(nodes.get(PAYMENT_DETAILS_TAG_POS)), CURRENCY_TAG_POS);
        if (currencyValue.matches(CURRENCY_REGEX)) {
            this.transactionDTO.setCurrency(currencyValue);
            return true;
        }
        return false;
    }

    private boolean setAmount(List<Node> nodes) {
        String amountValue = getStringValue(deleteWhitespaces(nodes.get(PAYMENT_DETAILS_TAG_POS)), AMOUNT_TAG_POS);
        if (amountValue.matches(AMOUNT_REGEX)) {
            this.transactionDTO.setAmount(amountValue);
            return true;
        }
        return false;
    }

    private boolean setDateTime(List<Node> nodes) {
        String dateTimeValue = getStringValue(nodes, DATE_TIME_TAG_POS);
        if (dateTimeValue.matches(DATE_TIME_REGEX)) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            this.transactionDTO.setDateTime(LocalDateTime.parse(dateTimeValue, format));
            return true;
        }
        return false;
    }

    private boolean setCustomerId(List<Node> nodes) {
        String customerIdValue = getStringValue(nodes.get(USER_TAG_POS), CUSTOMER_ID_TAG_POS);
        if (customerIdValue.matches(UUID_REGEX)) {
            this.transactionDTO.setCustomerId(customerIdValue);
            return true;
        }
        return false;
    }

    private boolean setTransactionId(List<Node> nodes) {
        String transactionIdValue = getStringValue(nodes, ID_TAG_POS);
        if (transactionIdValue.matches(UUID_REGEX)) {
            this.transactionDTO.setTransactionId(transactionIdValue);
            return true;
        }
        return false;
    }

    private String getStringValue(List<Node> nodes, int index) {
        Node transactionId = nodes.get(index);
        return transactionId.getTextContent();
    }

    private String getStringValue(Node node, int index) {
        Node transactionId = node.getChildNodes().item(index);
        return transactionId.getTextContent();
    }
}
