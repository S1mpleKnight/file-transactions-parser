package by.itechart.lastcoursetask.exception;

public class InvalidFileExtensionException extends RuntimeException {
    public InvalidFileExtensionException(String message) {
        super("Invalid file extension: " + message);
    }
}
