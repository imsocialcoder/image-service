package nl.debijenkorf.imageservice.service.imagedownload;

import nl.debijenkorf.imageservice.exception.SourceImageNotFoundException;

public interface ImageDownloadService {
    byte[] downloadImage(String reference) throws SourceImageNotFoundException;
}
