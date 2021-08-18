package by.itechart.lastcoursetask.exception;

public class FileNotReadException extends RuntimeException {
    public FileNotReadException(String message) {
        super("File was not read: " + message);
    }
}
