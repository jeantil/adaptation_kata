package converters;

import static builders.model.SmsEventModelTestBuilder.sms;
import static org.fest.assertions.api.Assertions.assertThat;
import java.util.UUID;
import org.junit.Test;
import api.domain.SmsEvent;
import builders.persistent.UserEntityTestBuilder;
import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import persistent.SmsEventEntity;
import persistent.UserEntity;

public class SmsConverterTest {

    @Test
    public void should_convert_from_model_to_entity() {
        // Given
        UUID id = TimeUUIDUtils.getUniqueTimeUUIDinMillis();
        Long userId = 12345L;
        String from = "+3301020304";
        String text = "dfgdfg";

        UserEntity userEntity = new UserEntityTestBuilder().userId(userId).build();

        SmsEvent model = sms() //
                .serverId(id)
                .text(text)
                .from(from)
                .build();
        SmsConverter smsConverter=new SmsConverter();
        // When
        SmsEventEntity entity=smsConverter.toEntity(model,userEntity);
        // Then
        assertThat(entity.getId()).isEqualTo(id);
        assertThat(entity.getUserId()).isEqualTo(userId);
        assertThat(entity.getFrom()).isEqualTo(from);
        assertThat(entity.getText()).isEqualTo(text);
    }
}
