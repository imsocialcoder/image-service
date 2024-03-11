package nl.debijenkorf.imageservice.service.logging;

import nl.debijenkorf.imageservice.config.ImageServiceLogDBConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!local")
public class CloudLoggingService implements LoggingService {
    private final ImageServiceLogDBConfiguration imageServiceLogDBConfiguration;
    @Autowired
    public CloudLoggingService(ImageServiceLogDBConfiguration imageServiceLogDBConfiguration) {
        this.imageServiceLogDBConfiguration = imageServiceLogDBConfiguration;
    }
    public void logException(Exception e, LogLevel logLevel){
        //TODO Using the configuration log exception to DB
    }
}
