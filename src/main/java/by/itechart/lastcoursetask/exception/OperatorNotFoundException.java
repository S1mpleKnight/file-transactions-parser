package by.itechart.lastcoursetask.exception;

public class OperatorNotFoundException extends RuntimeException{
    public OperatorNotFoundException(String message) {
        super("Operator not found: " + message);
    }
}
