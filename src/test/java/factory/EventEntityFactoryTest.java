package factory;

import static builders.domain.SmsEventModelTestBuilder.sms;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.net.URL;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import api.domain.AudioEvent;
import api.domain.FileEvent;
import api.domain.SmsEvent;
import api.domain.TextEvent;
import builders.domain.SmsEventModelTestBuilder;
import builders.domain.TextEventModelTestBuilder;
import builders.persistent.UserEntityTestBuilder;
import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import persistent.AudioEventEntity;
import persistent.EventEntity;
import persistent.FileEventEntity;
import persistent.SmsEventEntity;
import persistent.TextEventEntity;
import persistent.UserEntity;
import services.URLService;

@RunWith(MockitoJUnitRunner.class)
public class EventEntityFactoryTest {

    @InjectMocks
    private EventEntityFactory factory;

    @Mock
    private URLService urlService;

    private final UserEntity userEntity = new UserEntityTestBuilder().build();

    @Test
    public void should_fill_base_properties() {
        UUID id = TimeUUIDUtils.getUniqueTimeUUIDinMillis();
        Long userId = 12345L;

        UserEntity userEntity = new UserEntityTestBuilder().userId(userId).build();

        SmsEvent model = sms() //
                .serverId(id)
                .build();

        EventEntity built = factory.toEntity(model, userEntity);

        assertThat(built.getId()).isEqualTo(id);
        assertThat(built.getUserId()).isEqualTo(userId);

    }

    @Test
    public void should_fill_audio_info() throws Exception {
        //Given
        final URL url = new URL("file://url");
        final String filename = "filename";

        final long duration = 1l;

        AudioEvent model = new AudioEvent();
        model.setUrl(url);
        model.setDuration(duration);
        when(urlService.fromUrl(url)).thenReturn(filename);

        //When
        EventEntity entity = factory.toEntity(model, userEntity);

        //Then
        assertThat(entity).isInstanceOf(AudioEventEntity.class);

        AudioEventEntity built = (AudioEventEntity) entity;

        verify(urlService).fromUrl(url);

        assertThat(built.getFilename()).isEqualTo(filename);
        assertThat(built.getDuration()).isEqualTo(duration);
    }

    @Test
    public void should_fill_file_info() throws Exception {
        //Given
        String filename = "urlService";
        URL url = new URL("file://temp");

        FileEvent model = new FileEvent();
        model.setUrl(url);

        when(urlService.fromUrl(url)).thenReturn(filename);

        //When
        final EventEntity entity = factory.toEntity(model, userEntity);

        //Then
        assertThat(entity).isInstanceOf(FileEventEntity.class);
        FileEventEntity built = (FileEventEntity) entity;
        verify(urlService).fromUrl(url);
        assertThat(built.getFilename()).isEqualTo(filename);
    }


    @Test
    public void should_fill_sms_info() throws Exception {
        String interlocutorMsisdn = "+3301020304", text = "dfgdfg";

        SmsEvent model = SmsEventModelTestBuilder.sms() //
                .from(interlocutorMsisdn) //
                .text(text).build();

        EventEntity build = factory.toEntity(model, userEntity);

        assertThat(build).isInstanceOf(SmsEventEntity.class);

        SmsEventEntity built = (SmsEventEntity) build;

        assertThat(built.getFrom()).isEqualTo(interlocutorMsisdn);
        assertThat(built.getText()).isEqualTo(text);
    }

    @Test
    public void should_fill_text_info() throws Exception {
        String xmppId = "1654";

        TextEvent model = TextEventModelTestBuilder.text() //
                .xmppId(xmppId) //
                .text("pouet") //
                .build();

        EventEntity build = factory.toEntity(model, userEntity);

        assertThat(build).isInstanceOf(TextEventEntity.class);

        TextEventEntity built = (TextEventEntity) build;

        assertThat(built.getXmppId()).isEqualTo(xmppId);
        assertThat(built.getText()).isEqualTo(model.getText());
    }
}
