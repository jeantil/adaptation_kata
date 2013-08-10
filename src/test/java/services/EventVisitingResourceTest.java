package services;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static builders.domain.AudioEventModelTestBuilder.audio;
import static builders.persistent.FileEventEntityTestBuilder.file;
import static builders.persistent.SmsEventEntityTestBuilder.sms;
import java.util.UUID;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import api.domain.Event;
import builders.persistent.UserEntityTestBuilder;
import persistent.EventEntity;
import persistent.FileEventEntity;
import persistent.UserEntity;
import persistent.XmppEventEntity;
import support.CryptoService;

public class EventVisitingResourceTest {
    @Test
    public void should_get_event_by_id() throws Exception {
        // Given
        UUID uuid = UUID.randomUUID();
        EventStore store = mock(EventStore.class);
        EventEntity entity = sms().id(uuid).build();

        given(store.findById(uuid, EventEntity.class)).willReturn(entity);
        EventVisitingResource eventVisitingResource = new EventVisitingResource(store, null, null);

        // When
        Event event = eventVisitingResource.get(uuid);

        // Then
        assertThat(event.getId()).isEqualTo(entity.getId());
    }

    @Test
    public void should_get_event_by_xmppid() throws Exception {
        // Given
        String xmppId = "xmppId";
        String hash = "hash";
        FileEventEntity entity = file().xmppId(xmppId).hash(hash).build();

        EventStore store = mock(EventStore.class);
        CryptoService cryptoService = mock(CryptoService.class);

        given(store.findByXmppId(xmppId, XmppEventEntity.class)).willReturn(entity);
        given(cryptoService.fromCryptoHash(entity.getCryptoHash())).willReturn(hash);
        EventVisitingResource eventVisitingResource = new EventVisitingResource(store, null, cryptoService);

        // When
        Event event = eventVisitingResource.get(xmppId);

        // Then
        assertThat(event.getId()).isEqualTo(entity.getId());
    }

    @Test
    public void should_post_event() throws Exception {
        // Given
        EventStore store = mock(EventStore.class);
        Event event = audio().build();
        CryptoService cryptoService = mock(CryptoService.class);
        Session session = mock(Session.class);
        UserEntity user = UserEntityTestBuilder.userWithRandomId().build();
        UUID uuid = UUID.randomUUID();

        given(session.getUser()).willReturn(user);
        given(store.persist(any(EventEntity.class))).willAnswer(persistedEventEntity(uuid));
        EventVisitingResource eventVisitingResource = new EventVisitingResource(store, session, cryptoService);
        // When
        Event actual = eventVisitingResource.post(event);
        // Then
        assertThat(actual.getId()).isNotNull();
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
