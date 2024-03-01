package nl.debijenkorf.imageservice.service.imagedownload;

import nl.debijenkorf.imageservice.config.ImageServiceDownloadConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.when;

@DisplayName("ImageDownloadService Tests")
class ImageDownloadServiceImplTest {

    @Mock
    private ImageServiceDownloadConfiguration imageServiceDownloadConfiguration;

    @InjectMocks
    private ImageDownloadServiceImpl imageDownloadService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Download Image Successfully")
    void downloadImage_ImageExists_Success() {
        String reference = "existing.jpg";

        when(imageServiceDownloadConfiguration.getRootUrl()).thenReturn("http://example.com");

        byte[] result = imageDownloadService.downloadImage(reference);

        // TODO: Add assertions based on the expected behavior later
        assertArrayEquals(new byte[0], result);
    }
}
