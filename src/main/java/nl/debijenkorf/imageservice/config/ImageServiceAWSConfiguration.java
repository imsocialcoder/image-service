package nl.debijenkorf.imageservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aws.s3")
@Getter
@Setter
public class ImageServiceAWSConfiguration {
    private String endpoint;
    private String accesskey;
    private String secretkey;

}
