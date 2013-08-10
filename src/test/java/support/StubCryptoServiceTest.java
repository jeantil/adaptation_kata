package support;

import static org.fest.assertions.api.Assertions.assertThat;
import java.net.MalformedURLException;
import org.junit.Test;
import persistent.support.CryptoHash;

public class StubCryptoServiceTest {

    @Test
    public void should_generate_crypto_hash_from_filename() throws MalformedURLException {
        // Given
        final StubCryptoService cryptoService = new StubCryptoService();

        // When
        final CryptoHash hash = cryptoService.toCryptoHash("filename");
        // Then
        assertThat(hash.getHash()).isEqualTo("emanelif");
    }

    @Test
    public void should_decrypt_hash_from_filename() throws MalformedURLException {
        // Given
        final StubCryptoService cryptoService = new StubCryptoService();

        // When
        final CryptoHash hash = cryptoService.toCryptoHash("filename");
        String filename = cryptoService.fromCryptoHash(hash);
        // Then
        assertThat(filename).isEqualTo("filename");
    }
}
