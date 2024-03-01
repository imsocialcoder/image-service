package nl.debijenkorf.imageservice.service.aws;

import nl.debijenkorf.imageservice.config.ImageServiceAWSConfiguration;
import nl.debijenkorf.imageservice.config.ImageServicePredefinedTypesConfiguration;
import nl.debijenkorf.imageservice.exception.PredefinedImageTypeNotFoundException;
import nl.debijenkorf.imageservice.exception.S3WriteException;
import nl.debijenkorf.imageservice.model.PredefinedImageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("AWSS3Service Tests")
class AWSS3ServiceImplTest {

    @Mock
    private ImageServiceAWSConfiguration imageServiceAWSConfigurationMock;

    @Mock
    private ImageServicePredefinedTypesConfiguration imageServicePredefinedTypesConfigurationMock;

    @InjectMocks
    private AWSS3ServiceImpl awsS3ServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Retrieve Optimized Image Successfully")
    void getOptimizedImageFromStorage_Success() throws IOException {
        String predefinedImageType = "existing";
        String reference = "abc.jpg";
        String classpath = "classpath:" + predefinedImageType + "/" + reference;
        byte[] expectedBytes = "exampleBytes".getBytes();

        // Mock the ResourceLoader and Resource
        ResourceLoader mockedResourceLoader = mock(ResourceLoader.class);
        Resource mockedResource = mock(Resource.class);
        PredefinedImageType predefinedImageTypeInstance = new PredefinedImageType();
        Map<String, PredefinedImageType> predefinedTypesMap = new HashMap<>();
        predefinedTypesMap.put("existing", predefinedImageTypeInstance);

        ImageServicePredefinedTypesConfiguration mockedConfiguration = mock(ImageServicePredefinedTypesConfiguration.class);
        when(mockedConfiguration.getTypes()).thenReturn(predefinedTypesMap);

        // Inject dependencies into the AWSS3Service instance
        AWSS3ServiceImpl awsS3ServiceImpl = new AWSS3ServiceImpl(imageServiceAWSConfigurationMock, mockedResourceLoader, mockedConfiguration);

        when(mockedResourceLoader.getResource(classpath)).thenReturn(mockedResource);
        when(mockedResource.getInputStream()).thenReturn(getInputStream(expectedBytes));

        byte[] result = awsS3ServiceImpl.getOptimizedImageFromStorage(predefinedImageType, reference);

        assertArrayEquals(expectedBytes, result);
    }

    @Test
    @DisplayName("Attempt to Retrieve Nonexistent Predefined Type Throws Exception")
    void getOptimizedImageFromStorage_PredefinedTypeNotFound() {
        String predefinedImageType = "nonexistentType";
        String reference = "exampleReference";

        Map<String, PredefinedImageType> predefinedTypesMap = new HashMap<>();
        when(imageServicePredefinedTypesConfigurationMock.getTypes()).thenReturn(predefinedTypesMap);

        assertThrows(PredefinedImageTypeNotFoundException.class, () -> awsS3ServiceImpl.getOptimizedImageFromStorage(predefinedImageType, reference));
    }

    @Test
    @DisplayName("Attempt to Save Image Throws S3WriteException")
    void saveImage_ThrowsS3WriteException() throws InterruptedException {
        String predefinedImageType = "exampleType";
        String reference = "exampleReference";
        byte[] optimizedImage = "exampleBytes".getBytes();

        assertThrows(S3WriteException.class, () -> awsS3ServiceImpl.saveImage(predefinedImageType, reference, optimizedImage));
    }

    private InputStream getInputStream(byte[] bytes) {
        return new InputStream() {
            int index = 0;

            @Override
            public int read() throws IOException {
                if (index == bytes.length) {
                    return -1;
                }
                return bytes[index++];
            }
        };
    }
}
