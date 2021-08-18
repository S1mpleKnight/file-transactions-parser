package by.itechart.lastcoursetask.exception;

public class TransactionExistException extends RuntimeException {

    public TransactionExistException(String message) {
        super("Transaction is already exist: " + message);
    }

}
