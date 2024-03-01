package nl.debijenkorf.imageservice.exception;

import nl.debijenkorf.imageservice.config.ImageServiceLogDBConfiguration;
import nl.debijenkorf.imageservice.service.aws.AWSS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
@Profile("!local")
public class CloudImageServiceExceptionHandler {

    private final ImageServiceLogDBConfiguration imageServiceLogDBConfiguration;
    private final AWSS3Service awss3Service;

    @Autowired
    public CloudImageServiceExceptionHandler(ImageServiceLogDBConfiguration imageServiceLogDBConfiguration, AWSS3Service awss3Service){
        this.imageServiceLogDBConfiguration = imageServiceLogDBConfiguration;
        this.awss3Service = awss3Service;
    }
    @ExceptionHandler(PredefinedImageTypeNotFoundException.class)
    public ResponseEntity<String> handlePredefinedImageTypeNotFound(PredefinedImageTypeNotFoundException e) {
        logExceptionToDB(e, LogLevel.INFO);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(SourceImageNotFoundException.class)
    public ResponseEntity<String> handleSourceImageNotFound(SourceImageNotFoundException e) {
        logExceptionToDB(e, LogLevel.INFO);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(SourceUrlDownException.class)
    public ResponseEntity<String> handleSourceUrlError(SourceUrlDownException e) {
        logExceptionToDB(e, LogLevel.ERROR);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(S3WriteException.class)
    public ResponseEntity<String> handleS3WriteException(S3WriteException e) throws IOException, InterruptedException {
        logExceptionToDB(e, LogLevel.WARN);
        try{
            Thread.sleep(200);
            awss3Service.saveImage(e.getPredefinedImageType(), e.getReference(), e.getOptimizedImage());
        }catch (S3WriteException exception){
            logExceptionToDB(exception, LogLevel.ERROR);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
        return null;
    }

    @ExceptionHandler(S3ConnectionException.class)
    public ResponseEntity<String> handleS3ConnectionException(S3ConnectionException e) {
        logExceptionToDB(e, LogLevel.ERROR);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    private void logExceptionToDB(Exception e, LogLevel logLevel){
        //TODO Using the configuration log exception to DB
    }
}
