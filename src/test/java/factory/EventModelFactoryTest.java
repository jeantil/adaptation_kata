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
import api.domain.AudioEvent;
import api.domain.FileEvent;
import api.domain.SmsEvent;
import builders.persistent.AudioEventEntityTestBuilder;
import builders.persistent.SmsEventEntityTestBuilder;
import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import persistent.AudioEventEntity;
import persistent.FileEventEntity;
import persistent.SmsEventEntity;
import persistent.support.CryptoAlgo;
import persistent.support.CryptoHash;
import support.CryptoService;

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

        SmsEvent built = factory.fromEntity(entity);

        assertThat(built.getId()).isEqualTo(id);
    }

    @Test
    public void should_fill_audio_info() throws Exception {
        //Given
        final String filename = "url";
        final long duration = 1l;
        final CryptoHash cryptoHash = new CryptoHash("url", CryptoAlgo.ULTIMATE_42);

        final AudioEventEntity entity = AudioEventEntityTestBuilder.audio().duration(duration).hash(filename).build();

        when(cryptoService.fromCryptoHash(cryptoHash)).thenReturn(filename);

        //When
        final AudioEvent built = factory.fromEntity(entity);

        //Then
        verify(cryptoService).fromCryptoHash(cryptoHash);

        assertThat(built.getFilename()).isEqualTo(filename);
        assertThat(built.getDuration()).isEqualTo(duration);
    }

    @Test
    public void should_fill_file_info() throws Exception {
        //Given
        String filename = "url";
        final CryptoHash cryptoHash = new CryptoHash("url",CryptoAlgo.ULTIMATE_42);

        FileEventEntity model = new FileEventEntity();
        model.setCryptoHash(cryptoHash);

        when(cryptoService.fromCryptoHash(cryptoHash)).thenReturn(filename);

        //When
        final FileEvent built= factory.fromEntity(model);

        //Then
        verify(cryptoService).fromCryptoHash(cryptoHash);
        assertThat(built.getFilename()).isEqualTo(filename);
    }


    @Test
    public void should_fill_sms_info() throws Exception {
        String interlocutorMsisdn = "+3301020304", text = "dfgdfg";

        SmsEventEntity entity = SmsEventEntityTestBuilder.sms() //
                .from(interlocutorMsisdn) //
                .text(text).build();

        SmsEvent built = factory.fromEntity(entity);

        assertThat(built.getFrom()).isEqualTo(interlocutorMsisdn);
        assertThat(built.getText()).isEqualTo(text);
    }
}
