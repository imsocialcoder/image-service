package nl.debijenkorf.imageservice.service.logging;

import nl.debijenkorf.imageservice.exception.LocalImageServiceExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("local")
public class LocalLoggingService implements LoggingService{
    private final Logger logger = LoggerFactory.getLogger(LocalImageServiceExceptionHandler.class);
    public void logException(Exception e, LogLevel logLevel){
        //TODO Using the configuration log exception to DB
    }
}
