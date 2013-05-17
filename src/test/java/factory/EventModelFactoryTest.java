package factory;

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
import builders.persistent.AudioEventEntityTestBuilder;
import builders.persistent.SmsEventEntityTestBuilder;
import builders.persistent.TextEventEntityTestBuilder;
import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import persistent.AudioEventEntity;
import persistent.FileEventEntity;
import persistent.SmsEventEntity;
import persistent.TextEventEntity;
import services.CryptoService;

@RunWith(MockitoJUnitRunner.class)

public class EventModelFactoryTest {

    @InjectMocks
    private EventModelFactory factory;

    @Mock
    private CryptoService cryptoService;

    @Test
    public void should_fill_base_properties() {
        UUID id = TimeUUIDUtils.getUniqueTimeUUIDinMillis();
        Long userId = 12345L;

        SmsEventEntity entity = SmsEventEntityTestBuilder.sms() //
                .id(id)
                .build();

        SmsEvent built = factory.visit(entity);

        assertThat(built.getId()).isEqualTo(id);
    }

    @Test
    public void should_fill_audio_info() throws Exception {
        //Given
        final String filename = "filename";
        final long duration = 1l;

        final AudioEventEntity entity = AudioEventEntityTestBuilder.audio().duration(duration).filename(filename).build
                ();

        when(cryptoService.decryptFilename(filename)).thenReturn(filename);

        //When
        final AudioEvent built = factory.visit(entity);

        //Then
        verify(cryptoService).decryptFilename(filename);

        assertThat(built.getFilename()).isEqualTo(filename);
        assertThat(built.getDuration()).isEqualTo(duration);
    }

    @Test
    public void should_fill_file_info() throws Exception {
        //Given
        String filename = "filename";

        FileEventEntity model = new FileEventEntity();
        model.setFilename(filename);

        when(cryptoService.decryptFilename(filename)).thenReturn(filename);

        //When
        final FileEvent built= factory.visit(model);

        //Then
        verify(cryptoService).decryptFilename(filename);
        assertThat(built.getFilename()).isEqualTo(filename);
    }


    @Test
    public void should_fill_sms_info() throws Exception {
        String interlocutorMsisdn = "+3301020304", text = "dfgdfg";

        SmsEventEntity entity = SmsEventEntityTestBuilder.sms() //
                .from(interlocutorMsisdn) //
                .text(text).build();

        SmsEvent built = factory.visit(entity);

        assertThat(built.getFrom()).isEqualTo(interlocutorMsisdn);
        assertThat(built.getText()).isEqualTo(text);
    }

    @Test
    public void should_fill_text_info() throws Exception {
        String xmppId = "1654";

        TextEventEntity entity = TextEventEntityTestBuilder.text() //
                .xmppId(xmppId) //
                .text("pouet") //
                .build();

        TextEvent built = factory.visit(entity);

        assertThat(built.getXmppId()).isEqualTo(xmppId);
        assertThat(built.getText()).isEqualTo(entity.getText());
    }
}
