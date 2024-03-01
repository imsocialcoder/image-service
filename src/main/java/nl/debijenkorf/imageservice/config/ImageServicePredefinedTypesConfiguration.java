package nl.debijenkorf.imageservice.config;

import lombok.Getter;
import lombok.Setter;
import nl.debijenkorf.imageservice.model.PredefinedImageType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "image-service.predefined-types")
@Getter
@Setter
public class ImageServicePredefinedTypesConfiguration {
    private Map<String, PredefinedImageType> types;
}

