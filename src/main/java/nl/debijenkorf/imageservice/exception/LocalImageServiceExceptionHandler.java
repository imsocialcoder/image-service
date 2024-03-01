package nl.debijenkorf.imageservice.exception;

import nl.debijenkorf.imageservice.service.aws.AWSS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
@Profile("local")
public class LocalImageServiceExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(LocalImageServiceExceptionHandler.class);
    private final AWSS3Service awss3Service;

    @Autowired
    public LocalImageServiceExceptionHandler(AWSS3Service awss3Service){
        this.awss3Service = awss3Service;
    }

    @ExceptionHandler(PredefinedImageTypeNotFoundException.class)
    public ResponseEntity<String> handlePredefinedImageTypeNotFound(PredefinedImageTypeNotFoundException e) {
        logger.info(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(SourceImageNotFoundException.class)
    public ResponseEntity<String> handleSourceImageNotFound(SourceImageNotFoundException e) {
        logger.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(SourceUrlDownException.class)
    public ResponseEntity<String> handleSourceUrlDownException(SourceUrlDownException e) {
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(S3WriteException.class)
    public ResponseEntity<String> handleS3WriteException(S3WriteException e) throws IOException, InterruptedException {
        logger.warn(e.getMessage());
        try{
            Thread.sleep(200);
            awss3Service.saveImage(e.getPredefinedImageType(), e.getReference(), e.getOptimizedImage());
        }catch (S3WriteException exception){
            logger.error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return null;
    }

    @ExceptionHandler(S3ConnectionException.class)
    public ResponseEntity<String> handleS3ConnectionException(S3ConnectionException e) {
        logger.error("There is no connection (or access) to S3.", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
