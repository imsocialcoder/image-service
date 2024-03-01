package nl.debijenkorf.imageservice.exception;

public class S3ConnectionException extends RuntimeException {
    public S3ConnectionException(String message) {
        super("No connection or access to S3");
    }
}
