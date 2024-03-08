package nl.debijenkorf.imageservice.service.image;

import nl.debijenkorf.imageservice.config.ImageServicePredefinedTypesConfiguration;
import nl.debijenkorf.imageservice.model.PredefinedImageType;
import nl.debijenkorf.imageservice.service.imagedownload.ImageDownloadService;
import nl.debijenkorf.imageservice.service.aws.AWSS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

    private final AWSS3Service awss3Service;
    private final ImageDownloadService imageDownloadService;
    private final ImageServicePredefinedTypesConfiguration imageServicePredefinedTypesConfiguration;

    @Autowired
    public ImageServiceImpl(AWSS3Service awss3Service, ImageDownloadService imageDownloadService, ImageServicePredefinedTypesConfiguration imageServicePredefinedTypesConfiguration) {
        this.awss3Service = awss3Service;
        this.imageDownloadService = imageDownloadService;
        this.imageServicePredefinedTypesConfiguration = imageServicePredefinedTypesConfiguration;
    }
    public byte[] getImage(String predefinedImageType, String reference) throws InterruptedException {
        //return optimized image from storage:
        byte[] imageFromStorage = awss3Service.getOptimizedImageFromStorage(predefinedImageType, reference);
        if(imageFromStorage != null){
            return imageFromStorage;
        }

        //Optimize original image and save it if exists in the storage
        //Call the method again
        if (isOriginalImageExistsOptimizeIt(predefinedImageType, reference)) return getImage(predefinedImageType, reference);

        //Download the original image and save the original one
        byte[] downloadedOriginalImage = imageDownloadService.downloadImage(reference);
        awss3Service.saveImage(predefinedImageType, reference, downloadedOriginalImage);

        //Optimize original image and save it if exists in the storage
        //Call the method again
        if (isOriginalImageExistsOptimizeIt(predefinedImageType, reference)) return getImage(predefinedImageType, reference);

        return new byte[0];
    }

    private boolean isOriginalImageExistsOptimizeIt(String predefinedImageType, String reference) throws InterruptedException {
        byte[] originalImageFromStorage = awss3Service.getOriginalImageFromStorage(reference);
        if(originalImageFromStorage != null){
            optimizeAndSaveImage(predefinedImageType, reference,originalImageFromStorage);
            return true;
        }
        return false;
    }

    private void optimizeAndSaveImage(String predefinedImageType, String reference, byte[] originalImageFromStorage) throws InterruptedException {
        byte[] optimizedImage = optimizeImage(predefinedImageType, originalImageFromStorage);
        awss3Service.saveImage(predefinedImageType, reference, optimizedImage);
    }

    private byte[] optimizeImage(String predefinedImageType, byte[] originalImage){
        PredefinedImageType predefinedImageTypeValue = imageServicePredefinedTypesConfiguration.getTypes().get(predefinedImageType);
        //TODO Optimize based on configuration

        return originalImage;
    }
    
    public void flushImage(String predefinedImageType, String reference) {
        awss3Service.flushImage(predefinedImageType, reference);
    }
}
