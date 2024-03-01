package nl.debijenkorf.imageservice.service.aws;

import nl.debijenkorf.imageservice.config.ImageServiceAWSConfiguration;
import nl.debijenkorf.imageservice.config.ImageServicePredefinedTypesConfiguration;
import nl.debijenkorf.imageservice.exception.PredefinedImageTypeNotFoundException;
import nl.debijenkorf.imageservice.exception.S3WriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class AWSS3ServiceImpl implements AWSS3Service {
    private final ImageServiceAWSConfiguration imageServiceAWSConfiguration;
    private final ResourceLoader resourceLoader;
    private final ImageServicePredefinedTypesConfiguration imageServicePredefinedTypesConfiguration;

    @Autowired
    public AWSS3ServiceImpl(ImageServiceAWSConfiguration imageServiceAWSConfiguration, ResourceLoader resourceLoader, ImageServicePredefinedTypesConfiguration imageServicePredefinedTypesConfiguration){
        this.imageServiceAWSConfiguration = imageServiceAWSConfiguration;
        this.resourceLoader = resourceLoader;
        this.imageServicePredefinedTypesConfiguration = imageServicePredefinedTypesConfiguration;
    }

    public byte[] getOptimizedImageFromStorage(String predefinedImageType, String reference) {
        if(!isPredefinedTypeExists(predefinedImageType))
            throw new PredefinedImageTypeNotFoundException(predefinedImageType);

        //Mock - Try to read image from file system:
        String classpath = generateClassPath(predefinedImageType, reference);
        Resource resource = resourceLoader.getResource(classpath);

        try (InputStream inputStream = resource.getInputStream()) {
            return inputStream.readAllBytes();
        } catch (IOException e) {
            return null;
        }
    }

    private static String generateClassPath(String predefinedImageType, String reference) {
        String classpath = "classpath:" + predefinedImageType + "/" + reference;

        if(reference.length() > 8) //TODO Change with correct indexing logic
            classpath = "classpath:" + predefinedImageType + "/" + reference.substring(0,4) + "/" + reference;

        return classpath;
    }

    private boolean isPredefinedTypeExists(String predefinedImageType){
        return imageServicePredefinedTypesConfiguration.getTypes().get(predefinedImageType) != null;
    }

    public void saveImage(String predefinedImageType, String reference, byte[] optimizedImage) throws InterruptedException {
        throw new S3WriteException(predefinedImageType, reference, optimizedImage);
    }

    public void flushImage(String predefinedImageType, String reference) {
        if(predefinedImageType.toLowerCase().equals("original")){
            flushAll(reference);
        }else{
            flushImageByTypeAndReference(predefinedImageType, reference);
        }
    }

    private void flushAll(String reference){
        //For each predefined type, delete images
    }

    private void flushImageByTypeAndReference(String predefinedImageType, String reference){
        //only for this type, delete image
    }

    public byte[] getOriginalImageFromStorage(String reference) {
        return new byte[0];
    }
}
