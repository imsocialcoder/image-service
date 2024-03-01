package nl.debijenkorf.imageservice.service.image;

import nl.debijenkorf.imageservice.service.aws.AWSS3ServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.*;

@DisplayName("ImageService Tests")
class ImageServiceImplTest {

    @Mock
    private AWSS3ServiceImpl awss3ServiceImplMock;

    @InjectMocks
    private ImageServiceImpl imageServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Retrieve Optimized Image Successfully")
    void getImage_Success() throws InterruptedException {
        String predefinedImageType = "thumbnail";
        String reference = "abc.jpg";
        byte[] expectedBytes = "exampleBytes".getBytes();
        when(awss3ServiceImplMock.getOptimizedImageFromStorage(predefinedImageType, reference)).thenReturn(expectedBytes);

        byte[] result = imageServiceImpl.getImage(predefinedImageType, reference);

        assertArrayEquals(expectedBytes, result);
    }

    @Test
    @DisplayName("Flush Image Successfully")
    void flushImage_Success() {
        String predefinedImageType = "thumbnail";
        String reference = "reference.jpg";

        doNothing().when(awss3ServiceImplMock).flushImage(predefinedImageType, reference);
        imageServiceImpl.flushImage(predefinedImageType, reference);

        verify(awss3ServiceImplMock, times(1)).flushImage(predefinedImageType, reference);
    }
}
