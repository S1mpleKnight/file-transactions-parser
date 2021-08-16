package by.itechart.lastcoursetask.exception;

public class RoleNotFoundException extends RuntimeException {
    private final String argument;

    public RoleNotFoundException(String argument) {
        this.argument = argument;
    }

    @Override
    public String getMessage() {
        return "Role not found: " + argument;
    }
}
