package by.itechart.lastcoursetask.handler;

import by.itechart.lastcoursetask.dto.TransactionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class SaxHandler extends DefaultHandler {
    private final static String ID_TAG = "id";
    private final static String USER_TAG = "user";
    private final static String TIMESTAMP_TAG = "timestamp";
    private final static String PAYMENT_DETAILS_TAG = "payment_details";
    private final static String AMOUNT_TAG = "amount";
    private final static String CURRENCY_TAG = "currency";
    private final static String STATUS_TAG = "status";
    private final static String TRANSACTION_TAG = "transaction";
    private final static String TRANSACTIONS_TAG = "transactions";
    private final static String SUCCESS_TRANSACTION_STATUS = "COMPLETE";
    private final static String DATE_TIME_REGEX = "[0-9]{4}(-[0-9]{2}){2} ([0-9]{2}:){2}[0-9]{2}";
    private final static String STATUS_REGEX = "((complete)|(failure))";
    private final static String CURRENCY_REGEX = "[a-z]{3}";
    private final static String AMOUNT_REGEX = "[0-9 ]+";
    private final static String ID_REGEX = "[0-9a-z]{8}-([0-9a-z]{4}-){3}[0-9a-z]{12}";

    private List<TransactionDto> transactionsList;
    private TransactionDto transactionDTO;
    private Boolean isTransactionId;
    private String currentTag;

    public SaxHandler() {
        this.isTransactionId = true;
    }

    public List<TransactionDto> getTransactionsList() {
        return this.transactionsList;
    }

    @Override
    public void startDocument() {
        log.info("Parsing XML file");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        this.currentTag = qName;
        if (qName.equals(TRANSACTION_TAG)) {
            this.transactionDTO = new TransactionDto();
        }
        if (qName.equals(TRANSACTIONS_TAG)) {
            this.transactionsList = new ArrayList<>();
        }
        if (qName.equals(USER_TAG)) {
            this.isTransactionId = false;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals(TRANSACTION_TAG)) {
            this.transactionsList.add(this.transactionDTO);
            this.transactionDTO = null;
        }
        if (qName.equals(USER_TAG)) {
            this.isTransactionId = true;
        }
        this.currentTag = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String data = new String(ch, start, length);
        if (data.contains("<") || currentTag == null) {
            return;
        }
        manipulateData(data);
    }

    private void manipulateData(String data) throws SAXException {
        trySetID(data);
        trySetAmount(data);
        trySetCurrency(data);
        trySetStatus(data);
        trySetDateTime(data);
    }

    private void trySetDateTime(String data) throws SAXException {
        if (currentTag.equals(TIMESTAMP_TAG)) {
            if (data.matches(DATE_TIME_REGEX)) {
                this.transactionDTO.setDateTime(data);
            } else {
                throw new SAXException("Invalid date/time value: " + data);
            }
        }
    }

    private void trySetStatus(String data) throws SAXException {
        if (currentTag.equals(STATUS_TAG)) {
            if (data.toLowerCase().matches(STATUS_REGEX)) {
                this.transactionDTO.setStatus(data.equals(SUCCESS_TRANSACTION_STATUS));
            } else {
                throw new SAXException("Invalid status value: " + data);
            }
        }
    }

    private void trySetCurrency(String data) throws SAXException {
        if (currentTag.equals(CURRENCY_TAG)) {
            if (data.toLowerCase().matches(CURRENCY_REGEX)) {
                this.transactionDTO.setCurrency(data.toLowerCase());
            } else {
                throw new SAXException("Invalid currency name: " + data);
            }
        }
    }

    private void trySetAmount(String data) throws SAXException {
        if (currentTag.equals(AMOUNT_TAG)) {
            if (data.matches(AMOUNT_REGEX)) {
                this.transactionDTO.setAmount(String.join("", data.split(" ")));
            } else {
                throw new SAXException("Invalid amount: " + data);
            }
        }
    }

    private void trySetID(String data) throws SAXException {
        if (currentTag.equals(ID_TAG)) {
            if (data.matches(ID_REGEX)) {
                fillTransaction(data);
            } else {
                throw new SAXException("Invalid id: " + data);
            }
        }
    }

    private void fillTransaction(String data) {
        if (isTransactionId) {
            this.transactionDTO.setTransactionId(data);
        } else {
            this.transactionDTO.setCustomerId(data);
        }
    }
}
