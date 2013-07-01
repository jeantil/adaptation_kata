package acceptance;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.IOException;
import org.codehaus.jackson.JsonNode;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import support.TrivialRouter;

public class StorageIT extends BaseAcceptanceIT {

    @Test
    public void should_store_an_sms_event() throws IOException {
        // Given
        final AnnotationConfigApplicationContext ctx = initializeApplication();

        final TrivialRouter router = ctx.getBean(TrivialRouter.class);
        /**
         * Service json {"type":"sms","sender":"+33605040302","sms":"coucou"}
         */
        final String smsJson = "{\"type\":\"sms\",\"sender\":\"+33605040302\",\"sms\":\"coucou\"}";

        // When
        final String result = router.route(TrivialRouter.Verb.POST, "/events", smsJson);

        // Then
        assertThat(result).matches("\\{\"type\":\"sms\",\"id\":(.*),\"sender\":\"\\+33605040302\"," +
                "\"sms\":\"coucou\"\\}");

        /**
         * Persistence json {"type":"sms","id":"...UUID...","from":"+33605040302","text":"coucou","userId":1}
         */
        final JsonNode jsonNode = readStoredFile(ctx);
        assertThat(jsonNode.isObject()).isTrue();
        assertThat(jsonNode.get("type").asText()).isEqualTo("sms");
        assertThat(jsonNode.get("from").asText()).isEqualTo("+33605040302");
        assertThat(jsonNode.get("text").asText()).isEqualTo("coucou");
        assertThat(jsonNode.get("userId").asLong()).isEqualTo(1l);
        assertThat(jsonNode.get("id").asText()).isNotEmpty();
    }

    @Test
    public void should_store_a_text_event() throws IOException {
        // Given
        final AnnotationConfigApplicationContext ctx = initializeApplication();

        final TrivialRouter router = ctx.getBean(TrivialRouter.class);
        /**
         * Service json  {"type":"text","xmppId":"XMPP001","text":"message of text event"}
         */
        final String textJson = "{\"type\":\"text\",\"xmppId\":\"XMPP001\",\"text\":\"message of text event\"}";

        // When
        final String result = router.route(TrivialRouter.Verb.POST, "/events", textJson);

        // Then
        assertThat(result).matches("\\{\"type\":\"text\",\"id\":(.*),\"xmppId\":\"XMPP001\"," +
                "\"text\":\"message of text event\"\\}");

        /**
         * Persistence json
         * {"type":"text","id":"...UUID...","message":"message of text event","xmppId":"XMPP001", "userId":1}
         */

        final JsonNode jsonNode = readStoredFile(ctx);
        assertThat(jsonNode.isObject()).isTrue();
        assertThat(jsonNode.get("type").asText()).isEqualTo("text");
        assertThat(jsonNode.get("message").asText()).isEqualTo("message of text event");
        assertThat(jsonNode.get("userId").asLong()).isEqualTo(1l);
        assertThat(jsonNode.get("id").asText()).isNotEmpty();
        assertThat(jsonNode.get("xmppId").asText()).isEqualTo("XMPP001");
    }

    @Test
    public void should_store_a_file_event() throws IOException {
        // Given
        final AnnotationConfigApplicationContext ctx = initializeApplication();

        final TrivialRouter router = ctx.getBean(TrivialRouter.class);
        /**
         * Service json
         * {"type":"file","xmppId":"XMPP002","url":"file:/filename"}
         */
        final String textJson = "{\"type\":\"file\",\"xmppId\":\"XMPP002\",\"url\":\"file:/filename\"}";

        // When
        final String result = router.route(TrivialRouter.Verb.POST, "/events", textJson);

        // Then
        assertThat(result).matches("\\{\"type\":\"file\",\"id\":(.*),\"xmppId\":\"XMPP002\"," +
                "\"url\":\"file:/filename\"\\}");
        /**
         * Persistence json
         * {"type":"file","id":"...UUID...","xmppId":"XMPP002","url":"file:/filename", "userId":1}
         */
        final JsonNode jsonNode = readStoredFile(ctx);
        assertThat(jsonNode.isObject()).isTrue();
        assertThat(jsonNode.get("type").asText()).isEqualTo("file");
        assertThat(jsonNode.get("filename").asText()).isEqualTo("/filename");
        assertThat(jsonNode.get("userId").asLong()).isEqualTo(1l);
        assertThat(jsonNode.get("id").asText()).isNotEmpty();
        assertThat(jsonNode.get("xmppId").asText()).isEqualTo("XMPP002");
    }

    @Test
    public void should_store_an_audio_event() throws IOException {
        // Given
        final AnnotationConfigApplicationContext ctx = initializeApplication();

        final TrivialRouter router = ctx.getBean(TrivialRouter.class);
        /**
         * Service json
         * {"type":"audio","xmppId":"XMPP003","url":"file:/filename","duration":15}
         */
        final String textJson = "{\"type\":\"audio\",\"xmppId\":\"XMPP003\",\"url\":\"file:/filename\"," +
                "\"duration\":15}";

        // When
        final String result = router.route(TrivialRouter.Verb.POST, "/events", textJson);

        // Then
        assertThat(result).matches("\\{\"type\":\"audio\",\"id\":(.*),\"xmppId\":\"XMPP003\"," +
                "\"url\":\"file:/filename\",\"duration\":15\\}");

        /**
         * Storage json
         * {"type":"audio","id":"...UUID...","xmppId":"XMPP003","url":"file:/filename","duration":15, "userId":1}
         */

        final JsonNode jsonNode = readStoredFile(ctx);
        assertThat(jsonNode.isObject()).isTrue();
        assertThat(jsonNode.get("type").asText()).isEqualTo("audio");
        assertThat(jsonNode.get("filename").asText()).isEqualTo("/filename");
        assertThat(jsonNode.get("duration").asInt()).isEqualTo(15);
        assertThat(jsonNode.get("userId").asLong()).isEqualTo(1l);
        assertThat(jsonNode.get("id").asText()).isNotEmpty();
        assertThat(jsonNode.get("xmppId").asText()).isEqualTo("XMPP003");
    }

}
