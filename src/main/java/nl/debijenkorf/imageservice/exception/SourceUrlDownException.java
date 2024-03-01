package nl.debijenkorf.imageservice.exception;

public class SourceUrlDownException extends RuntimeException {
    public SourceUrlDownException(String message) {
        super("Source URL is down");
    }
}
