package services;

import static org.fest.assertions.api.Assertions.assertThat;
import java.util.UUID;
import org.junit.Test;
import builders.persistent.AudioEventEntityTestBuilder;
import persistent.AudioEventEntity;

public class EventStoreTest {

    @Test
    public void should_store_event_entities() {
        // Given
        final AudioEventEntity event = AudioEventEntityTestBuilder.audio().id(UUID.randomUUID()).build();
        final EventStore eventStore = new EventStore();
        // When
        eventStore.persist(event);
        final AudioEventEntity byId = eventStore.findById(event.getId());

        // Then
        assertThat(byId).isEqualTo(event);
    }
    @Test
    public void should_find_xmpp_events_by_xmppId() {
        // Given
        final String xmppId = "id";
        final AudioEventEntity event = AudioEventEntityTestBuilder.audio().id(UUID.randomUUID()).xmppId(xmppId).build();
        final EventStore eventStore = new EventStore();
        eventStore.persist(event);
        // When
        final AudioEventEntity byId = eventStore.findByXmppId(event.getXmppId());

        // Then
        assertThat(byId).isEqualTo(event);
    }
}
