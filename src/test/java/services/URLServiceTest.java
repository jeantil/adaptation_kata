package services;

import static org.fest.assertions.api.Assertions.assertThat;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.Test;

public class URLServiceTest {

    @Test
    public void should_generate_url_from_filename() throws MalformedURLException {
        // Given
        final URLService urlService = new URLService("file://");

        // When
        final URL filename = urlService.toUrl("url");
        // Then
        assertThat(filename.toString()).isEqualTo("file://url");
    }
}
