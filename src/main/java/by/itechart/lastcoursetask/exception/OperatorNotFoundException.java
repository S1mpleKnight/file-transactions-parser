package by.itechart.lastcoursetask.exception;

public class OperatorNotFoundException extends RuntimeException{
    private final String argument;

    public OperatorNotFoundException(String argument) {
        this.argument = argument;
    }

    @Override
    public String getMessage() {
        return "Operator not found: " + argument;
    }
}
