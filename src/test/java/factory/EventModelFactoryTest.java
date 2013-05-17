package factory;

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
import builders.persistent.AudioEventEntityTestBuilder;
import builders.persistent.SmsEventEntityTestBuilder;
import builders.persistent.TextEventEntityTestBuilder;
import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import persistent.AudioEventEntity;
import persistent.FileEventEntity;
import persistent.SmsEventEntity;
import persistent.TextEventEntity;
import services.URLService;

@RunWith(MockitoJUnitRunner.class)

public class EventModelFactoryTest {

    @InjectMocks
    private EventModelFactory factory;

    @Mock
    private URLService urlService;

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
        final String filename = "url";
        final long duration = 1l;
        final URL url = new URL("file://temp");

        final AudioEventEntity entity = AudioEventEntityTestBuilder.audio().duration(duration).filename(filename).build
                ();

        when(urlService.toUrl(filename)).thenReturn(url);

        //When
        final AudioEvent built = factory.visit(entity);

        //Then
        verify(urlService).toUrl(filename);

        assertThat(built.getUrl()).isEqualTo(url);
        assertThat(built.getDuration()).isEqualTo(duration);
    }

    @Test
    public void should_fill_file_info() throws Exception {
        //Given
        String filename = "url";
        final URL url = new URL("file://temp");

        FileEventEntity model = new FileEventEntity();
        model.setFilename(filename);

        when(urlService.toUrl(filename)).thenReturn(url);

        //When
        final FileEvent built= factory.visit(model);

        //Then
        verify(urlService).toUrl(filename);
        assertThat(built.getUrl()).isEqualTo(url);
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
