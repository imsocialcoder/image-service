package nl.debijenkorf.imageservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PredefinedImageType {
    private int height;
    private int width;
    private int quality;
    private ScaleType scaleType;
    private String fillColor;
    private ImageType type;
}

