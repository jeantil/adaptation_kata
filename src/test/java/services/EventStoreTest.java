package services;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import builders.persistent.AudioEventEntityTestBuilder;
import persistent.AudioEventEntity;

public class EventStoreTest {
    @Rule
    public TemporaryFolder folder= new TemporaryFolder();


    @Test
    public void should_store_event_entities() throws IOException {
        // Given
        final AudioEventEntity event = AudioEventEntityTestBuilder.audio().id(UUID.randomUUID()).build();
        final ObjectMapper mapper = mock(ObjectMapper.class);
        final String storePath = folder.getRoot().getAbsolutePath();
        final EventStore eventStore = new EventStore(mapper, storePath);
        final File file = new File(storePath + File.separator + event.getId() + ".json");
        // When
        eventStore.persist(event);

        // Then
        verify(mapper).writeValue(file, event);
    }
    @Test
    public void should_load_event_entities() throws IOException {
        // Given
        final AudioEventEntity event = AudioEventEntityTestBuilder.audio().id(UUID.randomUUID()).build();
        final ObjectMapper mapper = mock(ObjectMapper.class);
        final EventStore eventStore = new EventStore(mapper, folder.getRoot().getAbsolutePath());
        when(mapper.readValue(any(File.class), any(Class.class))).thenReturn(event);
        // When
        final AudioEventEntity byId = eventStore.findById(event.getId(),AudioEventEntity.class);

        // Then
        assertThat(byId).isEqualTo(event);
    }



    @Test
    public void should_find_xmpp_events_by_xmppId() throws IOException {
        // Given
        final String xmppId = "id";
        final AudioEventEntity event = AudioEventEntityTestBuilder.audio().id(UUID.randomUUID()).xmppId(xmppId).build();
        final ObjectMapper mapper = mock(ObjectMapper.class);
        final String storePath = folder.getRoot().getAbsolutePath();
        final File file = folder.newFile(event.getId() + ".json");
        final EventStore eventStore = new EventStore(mapper, storePath);
        when(mapper.readValue(file, AudioEventEntity.class)).thenReturn(event);
        // When
        final AudioEventEntity byId = eventStore.findByXmppId(event.getXmppId(), AudioEventEntity.class);

        // Then
        assertThat(byId).isEqualTo(event);
    }
}
