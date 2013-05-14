package converters;

import static builders.model.FileEventModelTestBuilder.file;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import java.util.UUID;
import org.junit.Test;
import api.event.domain.FileEvent;
import builders.persistent.UserEntityTestBuilder;
import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import persistent.FileEventEntity;
import persistent.UserEntity;
import services.CryptoService;

public class FileConverterTest {

    @Test
    public void should_convert_model_to_entity() {
        // Given
        UUID id = TimeUUIDUtils.getUniqueTimeUUIDinMillis();
        Long userId = 12345L;
        String filename = "filename";
        String xmppId = "1654";

        UserEntity userEntity = new UserEntityTestBuilder().userId(userId).build();

        final CryptoService cryptoService = mock(CryptoService.class);

        FileEvent model = file()
                .serverId(id)
                .xmppId(xmppId)
                .filename(filename)
                .build();

        FileConverter fileConverter= new FileConverter(cryptoService);
        // When
        final FileEventEntity file = fileConverter.toEntity(model, userEntity);
        // Then
        assertThat(file.getId()).isEqualTo(id);
        assertThat(file.getUserId()).isEqualTo(userId);
        assertThat(file.getXmppId()).isEqualTo(xmppId);
        assertThat(file.getFilename()).isEqualTo("filename");
    }

}
