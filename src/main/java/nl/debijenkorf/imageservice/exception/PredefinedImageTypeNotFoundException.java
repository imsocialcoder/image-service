package nl.debijenkorf.imageservice.exception;

public class PredefinedImageTypeNotFoundException extends RuntimeException {
    public PredefinedImageTypeNotFoundException(String predefinedImageType) {
        super("The requested predefined image type does not exist: " + predefinedImageType);
    }
}
