package converters;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import org.junit.Test;
import api.domain.FileEvent;
import builders.domain.FileEventModelTestBuilder;
import builders.persistent.FileEventEntityTestBuilder;
import builders.persistent.UserEntityTestBuilder;
import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import persistent.FileEventEntity;
import persistent.UserEntity;
import persistent.support.CryptoAlgo;
import persistent.support.CryptoHash;
import support.CryptoService;

public class FileConverterTest {

    @Test
    public void should_convert_model_to_entity() throws MalformedURLException {
        // Given
        UUID id = TimeUUIDUtils.getUniqueTimeUUIDinMillis();
        Long userId = 12345L;
        CryptoHash cryptoHash = new CryptoHash("url", CryptoAlgo.ULTIMATE_42);
        String encryptedFilename = "encryptedFilename";
        String xmppId = "1654";

        UserEntity userEntity = new UserEntityTestBuilder().userId(userId).build();

        final CryptoService CryptoService = mock(CryptoService.class);
        when(CryptoService.toCryptoHash(encryptedFilename)).thenReturn(cryptoHash);

        FileEvent model = FileEventModelTestBuilder.file()
                .serverId(id)
                .xmppId(xmppId)
                .filename(encryptedFilename)
                .build();

        FileConverter fileConverter= new FileConverter(CryptoService);
        // When
        final FileEventEntity file = fileConverter.toEntity(model, userEntity);
        // Then
        assertThat(file.getId()).isEqualTo(id);
        assertThat(file.getUserId()).isEqualTo(userId);
        assertThat(file.getXmppId()).isEqualTo(xmppId);
        assertThat(file.getCryptoHash()).isEqualTo(cryptoHash);
    }
    @Test
    public void should_convert_entity_to_model() throws MalformedURLException {
        // Given
        UUID id = TimeUUIDUtils.getUniqueTimeUUIDinMillis();
        Long userId = 12345L;
        CryptoHash cryptoHash = new CryptoHash("url",CryptoAlgo.ULTIMATE_42);
        String encryptedFilename = "encryptedFilename";
        String xmppId = "1654";

        UserEntity userEntity = new UserEntityTestBuilder().userId(userId).build();

        final CryptoService cryptoService = mock(CryptoService.class);
        when(cryptoService.fromCryptoHash(cryptoHash)).thenReturn(encryptedFilename);

        FileEventEntity entity = FileEventEntityTestBuilder.file()
                .id(id)
                .xmppId(xmppId)
                .hash("url")
                .build();

        FileConverter fileConverter= new FileConverter(cryptoService);
        // When
        final FileEvent file = fileConverter.fromEntity(entity);
        // Then
        assertThat(file.getId()).isEqualTo(id);
        assertThat(file.getXmppId()).isEqualTo(xmppId);
        assertThat(file.getFilename()).isEqualTo(encryptedFilename);
    }

}
