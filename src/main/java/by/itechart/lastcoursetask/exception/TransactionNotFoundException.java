package by.itechart.lastcoursetask.exception;

public class TransactionNotFoundException extends RuntimeException{
    private final String argument;

    public TransactionNotFoundException(String argument) {
        this.argument = argument;
    }

    @Override
    public String getMessage() {
        return "Transaction not found: " + argument;
    }
}
