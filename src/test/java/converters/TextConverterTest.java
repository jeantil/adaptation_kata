package converters;

import static org.fest.assertions.api.Assertions.assertThat;
import java.util.UUID;
import org.junit.Test;
import builders.model.TextEventModelTestBuilder;
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
}
