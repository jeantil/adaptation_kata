package converters;

import static builders.model.AudioEventModelTestBuilder.audio;
import static builders.model.FileEventModelTestBuilder.file;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.UUID;
import org.junit.Test;
import api.event.domain.AudioEvent;
import api.event.domain.FileEvent;
import builders.persistent.UserEntityTestBuilder;
import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import persistent.AudioEventEntity;
import persistent.FileEventEntity;
import persistent.UserEntity;
import services.CryptoService;

public class AudioConverterTest {

    @Test
    public void should_convert_model_to_entity() {
        // Given
        UUID id = TimeUUIDUtils.getUniqueTimeUUIDinMillis();
        Long userId = 12345L;
        String filename = "filename";
        String encryptedFilename = "encryptedFilename";
        String xmppId = "1654";
        final int duration = 10;

        UserEntity userEntity = new UserEntityTestBuilder().userId(userId).build();

        final CryptoService cryptoService = mock(CryptoService.class);
        when(cryptoService.cryptFilename(filename)).thenReturn(encryptedFilename);

        AudioEvent model = audio()
                .serverId(id)
                .xmppId(xmppId)
                .filename(filename)
                .duration(duration)
                .build();

        AudioConverter audioConverter= new AudioConverter(cryptoService);
        // When
        final AudioEventEntity file = audioConverter.toEntity(model, userEntity);
        // Then
        assertThat(file.getId()).isEqualTo(id);
        assertThat(file.getUserId()).isEqualTo(userId);
        assertThat(file.getXmppId()).isEqualTo(xmppId);
        assertThat(file.getFilename()).isEqualTo(encryptedFilename);
        assertThat(file.getDuration()).isEqualTo(duration);
    }

}
