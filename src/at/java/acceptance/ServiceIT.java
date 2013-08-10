package acceptance;

import java.io.IOException;
import java.util.UUID;
import org.fest.assertions.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import persistent.AudioEventEntity;
import persistent.EventEntity;
import persistent.FileEventEntity;
import persistent.SmsEventEntity;
import persistent.support.CryptoAlgo;
import persistent.support.CryptoHash;
import services.EventStore;
import support.Router;

public class ServiceIT extends BaseAcceptanceIT {

    @Test
    public void should_read_sms_event() throws IOException {
        final AnnotationConfigApplicationContext ctx = initializeApplication();
        final Router router = ctx.getBean(Router.class);
        // Given
        UUID id = UUID.randomUUID();
        final SmsEventEntity sms = new SmsEventEntity();
        sms.setFrom("+33605040302");
        sms.setText("coucou");
        sms.setId(id);
        sms.setUserId(1l);
        persist(ctx, sms);

        // When
        final String json = router.route(Router.Verb.GET, "/events", id.toString());

        // Then
        Assertions.assertThat(json).matches("\\{\"type\":\"sms\",\"id\":(.*),\"sender\":\"\\+33605040302\"," +
                "\"sms\":\"coucou\"\\}");
    }

    @Test
    public void should_read_file_event() throws IOException {
        final AnnotationConfigApplicationContext ctx = initializeApplication();
        final Router router = ctx.getBean(Router.class);
        // Given
        UUID id = UUID.randomUUID();
        final FileEventEntity text = new FileEventEntity();
        text.setCryptoHash(new CryptoHash("hash", CryptoAlgo.ULTIMATE_42));
        text.setXmppId("XMPP002");
        text.setId(id);
        text.setUserId(1l);
        persist(ctx, text);

        // When
        final String json = router.route(Router.Verb.GET, "/events", id.toString());

        // Then
        Assertions.assertThat(json).matches("\\{\"type\":\"file\",\"id\":(.*),\"xmppId\":\"XMPP002\"," +
                "\"filename\":\"hsah\"\\}");
    }

    @Test
    public void should_read_audio_event() throws IOException {
        final AnnotationConfigApplicationContext ctx = initializeApplication();
        final Router router = ctx.getBean(Router.class);
        // Given
        UUID id = UUID.randomUUID();
        final AudioEventEntity text = new AudioEventEntity();
        text.setDuration(15);
        text.setCryptoHash(new CryptoHash("hash", CryptoAlgo.ULTIMATE_42));
        text.setXmppId("XMPP003");
        text.setId(id);
        text.setUserId(1l);
        persist(ctx, text);

        // When
        final String json = router.route(Router.Verb.GET, "/events", id.toString());

        // Then
        Assertions.assertThat(json).matches("\\{\"type\":\"audio\",\"id\":(.*),\"xmppId\":\"XMPP003\"," +
                "\"filename\":\"hsah\",\"duration\":15\\}");
    }


    @Test
    public void should_retrieve_event_by_xmppId() throws IOException {
        final AnnotationConfigApplicationContext ctx = initializeApplication();
        final Router router = ctx.getBean(Router.class);
        // Given
        UUID id = UUID.randomUUID();
        final FileEventEntity file = new FileEventEntity();
        file.setCryptoHash(new CryptoHash("hash2", CryptoAlgo.ULTIMATE_42));
        file.setXmppId("XMPP002");
        file.setId(id);
        file.setUserId(1l);
        persist(ctx, file);

        id = UUID.randomUUID();
        final AudioEventEntity audio = new AudioEventEntity();
        audio.setDuration(15);
        audio.setCryptoHash(new CryptoHash("hash3", CryptoAlgo.ULTIMATE_42));
        audio.setXmppId("XMPP003");
        audio.setId(id);
        audio.setUserId(1l);
        persist(ctx, audio);

        // When
        final String json = router.route(Router.Verb.GET, "/events/xmpp", "XMPP002");

        // Then
        Assertions.assertThat(json).matches("\\{\"type\":\"file\",\"id\":(.*),\"xmppId\":\"XMPP002\"," +
                "\"filename\":\"2hsah\"\\}");
    }

    private void persist(AnnotationConfigApplicationContext ctx, EventEntity event) {
        final EventStore store = ctx.getBean(EventStore.class);
        store.persist(event);
    }
}
