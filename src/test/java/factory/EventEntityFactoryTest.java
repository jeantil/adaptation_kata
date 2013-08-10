package factory;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static builders.domain.SmsEventModelTestBuilder.sms;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import api.domain.AudioEvent;
import api.domain.FileEvent;
import api.domain.SmsEvent;
import builders.domain.SmsEventModelTestBuilder;
import builders.persistent.UserEntityTestBuilder;
import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import persistent.AudioEventEntity;
import persistent.EventEntity;
import persistent.FileEventEntity;
import persistent.SmsEventEntity;
import persistent.UserEntity;
import persistent.support.CryptoAlgo;
import persistent.support.CryptoHash;
import support.CryptoService;

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

        EventEntity built = factory.toEntity(model, userEntity);

        assertThat(built.getId()).isEqualTo(id);
        assertThat(built.getUserId()).isEqualTo(userId);

    }

    @Test
    public void should_fill_audio_info() throws Exception {
        //Given
        final CryptoHash cryptoHash = new CryptoHash("url",CryptoAlgo.ULTIMATE_42);
        final String filename = "filename";

        final long duration = 1l;

        AudioEvent model = new AudioEvent();
        model.setFilename(filename);
        model.setDuration(duration);
        when(cryptoService.toCryptoHash(filename)).thenReturn(cryptoHash);

        //When
        EventEntity entity = factory.toEntity(model, userEntity);

        //Then
        assertThat(entity).isInstanceOf(AudioEventEntity.class);

        AudioEventEntity built = (AudioEventEntity) entity;

        verify(cryptoService).toCryptoHash(filename);

        assertThat(built.getCryptoHash()).isEqualTo(cryptoHash);
        assertThat(built.getDuration()).isEqualTo(duration);
    }

    @Test
    public void should_fill_file_info() throws Exception {
        //Given
        String filename = "cryptoService";
        CryptoHash cryptoHash = new CryptoHash("url", CryptoAlgo.ULTIMATE_42);

        FileEvent model = new FileEvent();
        model.setFilename(filename);

        when(cryptoService.toCryptoHash(filename)).thenReturn(cryptoHash);

        //When
        final EventEntity entity = factory.toEntity(model, userEntity);

        //Then
        assertThat(entity).isInstanceOf(FileEventEntity.class);
        FileEventEntity built = (FileEventEntity) entity;
        verify(cryptoService).toCryptoHash(filename);
        assertThat(built.getCryptoHash()).isEqualTo(cryptoHash);
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
}
