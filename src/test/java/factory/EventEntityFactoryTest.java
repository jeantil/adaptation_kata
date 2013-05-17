package factory;

import static builders.model.SmsEventModelTestBuilder.sms;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import api.event.domain.AudioEvent;
import api.event.domain.FileEvent;
import api.event.domain.SmsEvent;
import api.event.domain.TextEvent;
import builders.model.SmsEventModelTestBuilder;
import builders.model.TextEventModelTestBuilder;
import builders.persistent.UserEntityTestBuilder;
import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import persistent.AudioEventEntity;
import persistent.EventEntity;
import persistent.FileEventEntity;
import persistent.SmsEventEntity;
import persistent.TextEventEntity;
import persistent.UserEntity;
import services.CryptoService;

@RunWith(MockitoJUnitRunner.class)
public class EventEntityFactoryTest {

    @InjectMocks
    private EventEntityFactory factory;

    @Mock
    private CryptoService cryptoService;

    private final UserEntity userEntity = new UserEntityTestBuilder().build();

    @Test
    public void should_fill_base_properties() {
        UUID id = TimeUUIDUtils.getUniqueTimeUUIDinMillis();
        Long userId = 12345L;

        UserEntity userEntity = new UserEntityTestBuilder().userId(userId).build();

        SmsEvent model = sms() //
                .serverId(id)
                .build();

        EventEntity built = model.accept(factory, userEntity);

        assertThat(built.getId()).isEqualTo(id);
        assertThat(built.getUserId()).isEqualTo(userId);

    }

    @Test
    public void should_fill_audio_info() throws Exception {
        //Given
        final String filename = "filename";
        final long duration = 1l;

        AudioEvent model = new AudioEvent();
        model.setFilename(filename);
        model.setDuration(duration);
        when(cryptoService.cryptFilename(filename)).thenReturn(filename);

        //When
        EventEntity entity = model.accept(factory, userEntity);

        //Then
        assertThat(entity).isInstanceOf(AudioEventEntity.class);

        AudioEventEntity built = (AudioEventEntity) entity;

        verify(cryptoService).cryptFilename(filename);

        assertThat(built.getFilename()).isEqualTo(filename);
        assertThat(built.getDuration()).isEqualTo(duration);
    }

    @Test
    public void should_fill_file_info() throws Exception {
        //Given
        String filename = "filename";

        FileEvent model = new FileEvent();
        model.setFilename(filename);

        when(cryptoService.cryptFilename(filename)).thenReturn(filename);

        //When
        final EventEntity entity = model.accept(factory, userEntity);

        //Then
        assertThat(entity).isInstanceOf(FileEventEntity.class);
        FileEventEntity built = (FileEventEntity) entity;
        verify(cryptoService).cryptFilename(filename);
        assertThat(built.getFilename()).isEqualTo(filename);
    }


    @Test
    public void should_fill_sms_info() throws Exception {
        String interlocutorMsisdn = "+3301020304", text = "dfgdfg";

        SmsEvent model = SmsEventModelTestBuilder.sms() //
                .from(interlocutorMsisdn) //
                .text(text).build();

        EventEntity build = model.accept(factory, userEntity);

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

        EventEntity build = model.accept(factory, userEntity);

        assertThat(build).isInstanceOf(TextEventEntity.class);

        TextEventEntity built = (TextEventEntity) build;

        assertThat(built.getXmppId()).isEqualTo(xmppId);
        assertThat(built.getText()).isEqualTo(model.getText());
    }
}
