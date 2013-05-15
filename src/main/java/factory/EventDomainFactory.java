package factory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import api.domain.Event;
import persistent.UserEntity;
import persistent.EventEntity;


/**
 * This is the reverse conversion from entity to model
 */
@Component
public class EventDomainFactory {

    @Autowired
    private EventModelFactory modelFactory;

    @Autowired
    private EventEntityFactory entityFactory;

    public Event fromEntity(EventEntity entity) {

        return entity.accept(modelFactory);
    }

    public Collection<Event> fromEntities(Collection<EventEntity> entities) {
        List<Event> result = new ArrayList<>();
        for (EventEntity entity : entities) {
            result.add(fromEntity(entity));
        }
        return result;
    }

    public EventEntity fromModel(Event model, UserEntity user) {
        return  entityFactory.toEntity(model,user);
    }
}
