package nl.debijenkorf.imageservice.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class S3WriteException extends RuntimeException {
    private final String predefinedImageType;
    private final String reference;
    private final byte[] optimizedImage;
    public S3WriteException(String predefinedImageType, String reference, byte[] optimizedImage) {
        super(String.format("Error writing the image to S3 storage with Predefined Type: %s and reference: %s", predefinedImageType, reference));
        this.reference = reference;
        this.predefinedImageType = predefinedImageType;
        this.optimizedImage = optimizedImage;
    }
}
