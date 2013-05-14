package converters;

import api.event.domain.Event;
import persistent.EventEntity;
import persistent.UserEntity;

abstract class EventConverter {

    public <T extends EventEntity, U extends Event> T toEntity(T event, U model, UserEntity user) {
        event.setId(model.getId());
        event.setUserId(user.getUserId());
        return event;
    }
}
