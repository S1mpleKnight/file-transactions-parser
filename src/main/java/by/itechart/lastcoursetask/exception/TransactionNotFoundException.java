package by.itechart.lastcoursetask.exception;

public class TransactionNotFoundException extends RuntimeException{
    public TransactionNotFoundException(String message) {
        super("Transaction not found: " + message);
    }
}
