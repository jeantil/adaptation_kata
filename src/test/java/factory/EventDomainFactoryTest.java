package factory;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import api.domain.Event;
import api.domain.FileEvent;
import api.domain.SmsEvent;
import builders.domain.FileEventModelTestBuilder;
import builders.domain.SmsEventModelTestBuilder;
import builders.persistent.FileEventEntityTestBuilder;
import builders.persistent.SmsEventEntityTestBuilder;
import builders.persistent.UserEntityTestBuilder;
import persistent.EventEntity;
import persistent.FileEventEntity;
import persistent.SmsEventEntity;
import persistent.UserEntity;

@RunWith(MockitoJUnitRunner.class)
public class EventDomainFactoryTest {

    @InjectMocks
    private EventDomainFactory factory;

    @Mock
    private EventModelFactory modelFactory;

    @Mock
    private EventEntityFactory entityFactory;

    @Test
    public void should_build_event_call_entity() throws Exception {

        SmsEvent sms = SmsEventModelTestBuilder.sms().build();
        SmsEventEntity built = SmsEventEntityTestBuilder.sms().build();
        UserEntity user = UserEntityTestBuilder.userWithRandomId().build();

        when(entityFactory.toEntity(sms, user)).thenReturn(built);

        //TODO : We would like to be able to assign to an EventEntitySms
        //final EventEntitySms eventEntity = factory.fromModel(sms, user);
        final EventEntity eventEntity = factory.fromModel(sms, user);

        assertThat(eventEntity).isSameAs(built);

    }

    @Test
    public void should_build_list_of_models() throws Exception {

        String hash = "hash";
        SmsEventEntity sms = SmsEventEntityTestBuilder.sms().build();
        FileEventEntity file = FileEventEntityTestBuilder.file().hash(hash).build();

        SmsEvent builtSms = SmsEventModelTestBuilder.sms().build();
        FileEvent builtFile = FileEventModelTestBuilder.file().filename(hash).build();

        when(modelFactory.fromEntity(sms)).thenReturn(builtSms);
        when(modelFactory.fromEntity(file)).thenReturn(builtFile);

        //TODO : We would like to be able to assign to the common parent
        //Collection<EventModelXmpp> builtList = factory.fromEntities(Arrays.asList(url, file));
        Collection<Event> builtList = factory.fromEntities(Arrays.asList((EventEntity)sms, file));

        assertThat(builtList).containsExactly(builtSms, builtFile);

    }

    @Test
    public void should_build_event_call_model() throws Exception {
        FileEventEntity file = FileEventEntityTestBuilder.file().hash("hash").build();
        FileEvent built = FileEventModelTestBuilder.file().build();

        when(modelFactory.fromEntity(file)).thenReturn(built);

        Event model = factory.fromEntity(file);

        assertThat(model).isSameAs(built);
    }
}
