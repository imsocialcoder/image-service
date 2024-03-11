package nl.debijenkorf.imageservice.service.logging;

import org.springframework.boot.logging.LogLevel;

public interface LoggingService {
    void logException(Exception e, LogLevel logLevel);
}
