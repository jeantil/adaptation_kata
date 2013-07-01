package converters;

import static org.fest.assertions.api.Assertions.assertThat;
import java.util.UUID;
import org.junit.Test;
import api.domain.TextEvent;
import builders.domain.TextEventModelTestBuilder;
import builders.persistent.TextEventEntityTestBuilder;
import builders.persistent.UserEntityTestBuilder;
import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import persistent.TextEventEntity;
import persistent.UserEntity;

public class TextConverterTest {

    @Test
    public void should_convert_model_to_entity() {
        UUID id = TimeUUIDUtils.getUniqueTimeUUIDinMillis();
        Long userId = 12345L;
        String xmppId = "1654";

        UserEntity userEntity = new UserEntityTestBuilder().userId(userId).build();

        api.domain.TextEvent model = TextEventModelTestBuilder.text() //
                .serverId(id)
                .xmppId(xmppId) //
                .text("pouet") //
                .build();

        TextConverter textConverter= new TextConverter();

        TextEventEntity entity = textConverter.toEntity(model, userEntity);

        assertThat(entity.getId()).isEqualTo(id);
        assertThat(entity.getUserId()).isEqualTo(userId);
        assertThat(entity.getXmppId()).isEqualTo(xmppId);
        assertThat(entity.getText()).isEqualTo(model.getText());
    }

    @Test
    public void should_convert_entity_to_model() {
        UUID id = TimeUUIDUtils.getUniqueTimeUUIDinMillis();
        String xmppId = "1654";

        TextEventEntity entity = TextEventEntityTestBuilder.text() //
                .id(id)
                .xmppId(xmppId) //
                .text("pouet") //
                .build();

        TextConverter textConverter= new TextConverter();

        final TextEvent model = textConverter.fromEntity(entity);

        assertThat(model.getId()).isEqualTo(id);
        assertThat(model.getXmppId()).isEqualTo(xmppId);
        assertThat(model.getText()).isEqualTo(model.getText());
    }
}
