package by.itechart.lastcoursetask.handler;

import by.itechart.lastcoursetask.dto.TransactionDTO;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

@Component
public class SAXHandler extends DefaultHandler {
    private final static String ID_TAG = "id";
    private final static String USER_TAG = "user";
    private final static String TIMESTAMP_TAG = "timestamp";
    private final static String PAYMENT_DETAILS_TAG = "payment_details";
    private final static String AMOUNT_TAG = "amount";
    private final static String CURRENCY_TAG = "currency";
    private final static String STATUS_TAG = "status";
    private final static String ROOT_TAG = "root";
    private final static String FIRST_NAME_TAG = "first_name";
    private final static String LAST_NAME_TAG = "last_name";
    private final static String EMAIL_TAG = "email";
    private final static String TRANSACTION_TAG = "transaction";
    private final static String TRANSACTIONS_TAG = "transactions";
    private final static String SUCCESS_TRANSACTION_STATUS = "COMPLETE";

    private List<TransactionDTO> transactionsList;
    private TransactionDTO transactionDTO;
    private Boolean isTransactionId;
    private String currentTag;

    public SAXHandler() {
        this.isTransactionId = true;
    }

    public List<TransactionDTO> getTransactionsList() {
        return this.transactionsList;
    }

    @Override
    public void startDocument() {
        //todo: add some logs
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        this.currentTag = qName;
        if (qName.equals(TRANSACTION_TAG)) {
            this.transactionDTO = new TransactionDTO();
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
    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length);
        if (data.contains("<") || currentTag == null) {
            return;
        }
        manipulateData(data);
    }

    private void manipulateData(String data) {
        trySetID(data);
        trySetAmount(data);
        trySetCurrency(data);
        trySetStatus(data);
        trySetDateTime(data);
    }

    private void trySetDateTime(String data) {
        if (currentTag.equals(TIMESTAMP_TAG)) {
            this.transactionDTO.setDateTime(data);
        }
    }

    private void trySetStatus(String data) {
        if (currentTag.equals(STATUS_TAG)) {
            this.transactionDTO.setStatus(data.equals(SUCCESS_TRANSACTION_STATUS));
        }
    }

    private void trySetCurrency(String data) {
        if (currentTag.equals(CURRENCY_TAG)) {
            this.transactionDTO.setCurrency(data.toLowerCase());
        }
    }

    private void trySetAmount(String data) {
        if (currentTag.equals(AMOUNT_TAG)) {
            this.transactionDTO.setAmount(String.join("", data.split(" ")));
        }
    }

    private void trySetID(String data) {
        if (currentTag.equals(ID_TAG)) {
            if (isTransactionId) {
                this.transactionDTO.setTransactionId(data);
            } else {
                this.transactionDTO.setCustomerId(data);
            }
        }
    }
}
