package by.itechart.lastcoursetask.exception;

public class TransactionExistException extends RuntimeException {
    private final String id;

    public TransactionExistException(String id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Transaction is already exist: " + id;
    }
}
