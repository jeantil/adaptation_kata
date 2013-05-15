package converters;

import static builders.model.FileEventModelTestBuilder.file;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import org.junit.Test;
import api.domain.FileEvent;
import builders.persistent.UserEntityTestBuilder;
import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import persistent.FileEventEntity;
import persistent.UserEntity;
import services.URLService;

public class FileConverterTest {

    @Test
    public void should_convert_model_to_entity() throws MalformedURLException {
        // Given
        UUID id = TimeUUIDUtils.getUniqueTimeUUIDinMillis();
        Long userId = 12345L;
        URL url = new URL("file://url");
        String encryptedFilename = "encryptedFilename";
        String xmppId = "1654";

        UserEntity userEntity = new UserEntityTestBuilder().userId(userId).build();

        final URLService urlService = mock(URLService.class);
        when(urlService.fromUrl(url)).thenReturn(encryptedFilename);

        FileEvent model = file()
                .serverId(id)
                .xmppId(xmppId)
                .url(url)
                .build();

        FileConverter fileConverter= new FileConverter(urlService);
        // When
        final FileEventEntity file = fileConverter.toEntity(model, userEntity);
        // Then
        assertThat(file.getId()).isEqualTo(id);
        assertThat(file.getUserId()).isEqualTo(userId);
        assertThat(file.getXmppId()).isEqualTo(xmppId);
        assertThat(file.getFilename()).isEqualTo(encryptedFilename);
    }

}
