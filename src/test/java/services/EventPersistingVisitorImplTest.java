package services;

import static java.util.UUID.randomUUID;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static builders.domain.AudioEventModelTestBuilder.audio;
import static builders.domain.FileEventModelTestBuilder.file;
import static builders.domain.SmsEventModelTestBuilder.sms;
import java.util.UUID;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import api.domain.AudioEvent;
import api.domain.FileEvent;
import api.domain.SmsEvent;
import builders.MsisdnGenerator;
import builders.persistent.UserEntityTestBuilder;
import persistent.AudioEventEntity;
import persistent.EventEntity;
import persistent.FileEventEntity;
import persistent.SmsEventEntity;
import persistent.UserEntity;
import support.CryptoService;

public class EventPersistingVisitorImplTest {
    @Test
    public void should_persist_an_smsEvent() throws Exception {
        // Given
        String from = MsisdnGenerator.randomizedFr();
        String text = "text";
        UserEntity userEntity = UserEntityTestBuilder.userWithRandomId().build();
        EventStore store = mock(EventStore.class);
        UUID uuid = randomUUID();
        when(store.persist(any(SmsEventEntity.class))).thenAnswer(persistedEventEntity(uuid));

        SmsEvent model = sms().from(from).text(text).build();

        EventPersistingVisitorImpl eventPersistingVisitor = new EventPersistingVisitorImpl(store, null, userEntity);
        // When
        eventPersistingVisitor.visit(model);
        // Then
        assertThat(model.getId()).isEqualTo(uuid);
    }

    @Test
    public void should_persist_a_fileEvent() throws Exception {
        // Given
        UserEntity userEntity = UserEntityTestBuilder.userWithRandomId().build();
        EventStore store = mock(EventStore.class);
        CryptoService cryptoService = mock(CryptoService.class);
        UUID uuid = randomUUID();
        String hash = "hash";
        String xmppId = "xmppId";
        FileEvent model = file().filename(hash).xmppId(xmppId).build();

        when(store.persist(any(FileEventEntity.class))).thenAnswer(persistedEventEntity(uuid));

        EventPersistingVisitorImpl eventPersistingVisitor = new EventPersistingVisitorImpl(store, cryptoService, userEntity);

        // When
        eventPersistingVisitor.visit(model);

        // Then
        assertThat(model.getId()).isEqualTo(uuid);
    }


    @Test
    public void should_persist_an_audioEvent() throws Exception {
        // Given
        UserEntity userEntity = UserEntityTestBuilder.userWithRandomId().build();
        EventStore store = mock(EventStore.class);
        CryptoService cryptoService = mock(CryptoService.class);
        UUID uuid = randomUUID();
        String hash = "hash";
        String xmppId = "xmppId";
        long duration = 2l;
        AudioEvent model = audio().filename(hash).duration(duration).xmppId(xmppId).build();

        when(store.persist(any(AudioEventEntity.class))).thenAnswer(persistedEventEntity(uuid));

        EventPersistingVisitorImpl eventPersistingVisitor = new EventPersistingVisitorImpl(store, cryptoService, userEntity);

        // When
        eventPersistingVisitor.visit(model);

        // Then
        assertThat(model.getId()).isEqualTo(uuid);
    }

    private Answer<EventEntity> persistedEventEntity(final UUID uuid) {
        return new Answer<EventEntity>() {
            @Override
            public EventEntity answer(InvocationOnMock invocation) throws Throwable {
                EventEntity eventEntity = (EventEntity) invocation.getArguments()[0];
                eventEntity.setId(uuid);
                return eventEntity;
            }
        };
    }
}
