package nl.debijenkorf.imageservice.service.imagedownload;

import nl.debijenkorf.imageservice.config.ImageServiceDownloadConfiguration;
import nl.debijenkorf.imageservice.exception.SourceImageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageDownloadServiceImpl implements ImageDownloadService {
    private final ImageServiceDownloadConfiguration imageServiceDownloadConfiguration;
    @Autowired
    public ImageDownloadServiceImpl(ImageServiceDownloadConfiguration imageServiceDownloadConfiguration){
        this.imageServiceDownloadConfiguration = imageServiceDownloadConfiguration;
    }
    public byte[] downloadImage(String reference){
        if(!isImageExists(reference))
            throw new SourceImageNotFoundException(reference);

        String sourceUrl = imageServiceDownloadConfiguration.getRootUrl();
        //TODO Download from source URL
        return new byte[0];
    }

    private boolean isImageExists(String reference){
        return true;
    }
}
