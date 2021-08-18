package by.itechart.lastcoursetask.exception;

public class OperatorExistException extends RuntimeException {
    public OperatorExistException(String message) {
        super("Operator is already exist, id: " + message);
    }
}
