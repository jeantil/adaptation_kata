package services;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static builders.persistent.AudioEventEntityTestBuilder.audio;
import static builders.persistent.FileEventEntityTestBuilder.file;
import static builders.persistent.SmsEventEntityTestBuilder.sms;
import org.junit.Test;
import api.domain.AudioEvent;
import api.domain.FileEvent;
import api.domain.SmsEvent;
import builders.MsisdnGenerator;
import persistent.AudioEventEntity;
import persistent.FileEventEntity;
import persistent.SmsEventEntity;
import support.CryptoService;

public class EventEntityVisitorImplTest {
    @Test
    public void should_convert_an_smsEventEntity_to_an_smsEvent() throws Exception {
        // Given
        Long userId = 1L;
        String from = MsisdnGenerator.randomizedFr();
        String text = "hi";
        SmsEventEntity sms = sms().from(from).text(text).userId(userId).build();
        EventEntityVisitorImpl eventEntityVisitor = new EventEntityVisitorImpl(null);
        // When
        SmsEvent actual = eventEntityVisitor.visit(sms);
        // Then
        assertThat(actual.getFrom()).isEqualTo(from);
        assertThat(actual.getText()).isEqualTo(text);
        assertThat(actual.getId()).isEqualTo(sms.getId());
    }

    @Test
    public void should_convert_a_file_event_entity_to_a_file_event() throws Exception {
        // Given
        long userId = 1L;
        String hash = "hash";
        String xmppId = "xmppId";
        FileEventEntity entity = file().userId(userId).hash(hash).xmppId(xmppId).build();
        CryptoService cryptoService = mock(CryptoService.class);
        when(cryptoService.fromCryptoHash(entity.getCryptoHash())).thenReturn(hash);
        EventEntityVisitorImpl eventEntityVisitor = new EventEntityVisitorImpl(cryptoService);
        // When
        FileEvent actual = eventEntityVisitor.visit(entity);
        // Then
        assertThat(actual.getFilename()).isEqualTo(hash);
        assertThat(actual.getXmppId()).isEqualTo(xmppId);
        assertThat(actual.getId()).isEqualTo(entity.getId());
    }

    @Test
    public void should_convert_an_audio_event_entity_to_an_audio_event() throws Exception {
        // Given
        long userId = 1L;
        String hash = "hash";
        String xmppId = "xmppId";
        long duration = 2l;
        AudioEventEntity entity = audio().duration(duration).hash(hash).userId(userId).xmppId(xmppId).build();
        CryptoService cryptoService = mock(CryptoService.class);
        when(cryptoService.fromCryptoHash(entity.getCryptoHash())).thenReturn(hash);
        EventEntityVisitorImpl eventEntityVisitor = new EventEntityVisitorImpl(cryptoService);
        // When
        AudioEvent actual = eventEntityVisitor.visit(entity);
        // Then
        assertThat(actual.getFilename()).isEqualTo(hash);
        assertThat(actual.getXmppId()).isEqualTo(xmppId);
        assertThat(actual.getDuration()).isEqualTo(duration);
        assertThat(actual.getId()).isEqualTo(entity.getId());

    }
}
