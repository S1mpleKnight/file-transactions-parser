package by.itechart.lastcoursetask.exception;

public class OperatorExistException extends RuntimeException {
    private final Long id;

    public OperatorExistException(Long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Operator is already exist, id: " + id;
    }
}
