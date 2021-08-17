package by.itechart.lastcoursetask.exception;

public class FileNotReadException extends RuntimeException {
    private final String filePath;

    public FileNotReadException(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getMessage() {
        return "File was not read: " + filePath;
    }
}
