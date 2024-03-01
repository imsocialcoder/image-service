package nl.debijenkorf.imageservice.service.aws;

public interface AWSS3Service {

    byte[] getOptimizedImageFromStorage(String predefinedImageType, String reference);

    void saveImage(String predefinedImageType, String reference, byte[] optimizedImage) throws InterruptedException;

    void flushImage(String predefinedImageType, String reference);

    byte[] getOriginalImageFromStorage(String reference);
}
