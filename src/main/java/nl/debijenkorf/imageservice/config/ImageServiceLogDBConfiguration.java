package nl.debijenkorf.imageservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "logdb")
@Getter
@Setter
public class ImageServiceLogDBConfiguration {
    private String endpoint;
    private String name;
    private String username;
    private String password;
}
