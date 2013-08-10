package support;


import static org.fest.assertions.api.Assertions.assertThat;
import org.junit.Test;
import persistent.support.CryptoAlgo;
import persistent.support.CryptoHash;

public class CryptoHashTest {
    @Test
    public void is_value_type() throws Exception {
        // When
        CryptoHash hash = new CryptoHash("String", CryptoAlgo.ULTIMATE_42);
        CryptoHash hash2 = new CryptoHash("String", CryptoAlgo.ULTIMATE_42);
        // Then
        assertThat(hash).isEqualTo(hash2);
        assertThat(hash.hashCode()).isEqualTo(hash2.hashCode());
        assertThat(hash.toString()).isEqualTo("CryptoHash(String)");
    }
}
