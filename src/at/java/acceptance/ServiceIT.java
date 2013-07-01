package acceptance;

import java.io.IOException;
import java.util.UUID;
import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import persistent.AudioEventEntity;
import persistent.EventEntity;
import persistent.FileEventEntity;
import persistent.SmsEventEntity;
import persistent.TextEventEntity;
import services.EventStore;
import support.TrivialRouter;

public class ServiceIT extends BaseAcceptanceIT {

    @Test
    public void should_read_sms_event() throws IOException {
        final AnnotationConfigApplicationContext ctx = initializeApplication();
        final TrivialRouter router = ctx.getBean(TrivialRouter.class);
        // Given
        UUID id = UUID.randomUUID();
        final SmsEventEntity sms = new SmsEventEntity();
        sms.setFrom("+33605040302");
        sms.setText("coucou");
        sms.setId(id);
        sms.setUserId(1l);
        persist(ctx, sms);

        // When
        final String json = router.route(TrivialRouter.Verb.GET, "/events", id.toString());

        // Then
        Assertions.assertThat(json).matches("\\{\"type\":\"sms\",\"id\":(.*),\"sender\":\"\\+33605040302\"," +
                "\"sms\":\"coucou\"\\}");
    }

    @Test
    public void should_read_text_event() throws IOException {
        final AnnotationConfigApplicationContext ctx = initializeApplication();
        final TrivialRouter router = ctx.getBean(TrivialRouter.class);
        // Given
        UUID id = UUID.randomUUID();
        final TextEventEntity text = new TextEventEntity();
        text.setText("coucou");
        text.setXmppId("XMPP001");
        text.setId(id);
        text.setUserId(1l);
        persist(ctx, text);

        // When
        final String json = router.route(TrivialRouter.Verb.GET, "/events", id.toString());

        // Then
        Assertions.assertThat(json).matches("\\{\"type\":\"text\",\"id\":(.*),\"xmppId\":\"XMPP001\"," +
                "\"text\":\"coucou\"\\}");
    }

    @Test
    public void should_read_file_event() throws IOException {
        final AnnotationConfigApplicationContext ctx = initializeApplication();
        final TrivialRouter router = ctx.getBean(TrivialRouter.class);
        // Given
        UUID id = UUID.randomUUID();
        final FileEventEntity text = new FileEventEntity();
        text.setFilename("/filename");
        text.setXmppId("XMPP002");
        text.setId(id);
        text.setUserId(1l);
        persist(ctx, text);

        // When
        final String json = router.route(TrivialRouter.Verb.GET, "/events", id.toString());

        // Then
        Assertions.assertThat(json).matches("\\{\"type\":\"file\",\"id\":(.*),\"xmppId\":\"XMPP002\"," +
                "\"url\":\"file:/filename\"\\}");
    }

    @Test
    public void should_read_audio_event() throws IOException {
        final AnnotationConfigApplicationContext ctx = initializeApplication();
        final TrivialRouter router = ctx.getBean(TrivialRouter.class);
        // Given
        UUID id = UUID.randomUUID();
        final AudioEventEntity text = new AudioEventEntity();
        text.setDuration(15);
        text.setFilename("/filename");
        text.setXmppId("XMPP003");
        text.setId(id);
        text.setUserId(1l);
        persist(ctx, text);

        // When
        final String json = router.route(TrivialRouter.Verb.GET, "/events", id.toString());

        // Then
        Assertions.assertThat(json).matches("\\{\"type\":\"audio\",\"id\":(.*),\"xmppId\":\"XMPP003\"," +
                "\"url\":\"file:/filename\",\"duration\":15\\}");
    }


    @Test
    public void should_retrieve_event_by_xmppId() throws IOException {
        final AnnotationConfigApplicationContext ctx = initializeApplication();
        final TrivialRouter router = ctx.getBean(TrivialRouter.class);
        // Given
        UUID id = UUID.randomUUID();
        final FileEventEntity text = new FileEventEntity();
        text.setFilename("/filename");
        text.setXmppId("XMPP002");
        text.setId(id);
        text.setUserId(1l);
        persist(ctx, text);

        id = UUID.randomUUID();
        final FileEventEntity file = new FileEventEntity();
        file.setFilename("/filename");
        file.setXmppId("XMPP002");
        file.setId(id);
        file.setUserId(1l);
        persist(ctx, file);

        id = UUID.randomUUID();
        final AudioEventEntity audio = new AudioEventEntity();
        audio.setDuration(15);
        audio.setFilename("/filename");
        audio.setXmppId("XMPP003");
        audio.setId(id);
        audio.setUserId(1l);
        persist(ctx, audio);

        // When
        final String json = router.route(TrivialRouter.Verb.GET, "/events/xmpp", "XMPP002");

        // Then
        Assertions.assertThat(json).matches("\\{\"type\":\"file\",\"id\":(.*),\"xmppId\":\"XMPP002\"," +
                "\"url\":\"file:/filename\"\\}");
    }

    private void persist(AnnotationConfigApplicationContext ctx, EventEntity event) throws IOException {
        final EventStore store= ctx.getBean(EventStore.class);
        store.persist(event);
    }
}
