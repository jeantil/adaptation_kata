package converters;

import api.event.domain.Event;
import persistent.EventEntity;
import persistent.UserEntity;

abstract class EventConverter<T extends EventEntity, U extends Event> {

    public abstract T toEntity(U model, UserEntity user);

    protected T toEntity(T event, U model, UserEntity user) {
        event.setId(model.getId());
        event.setUserId(user.getUserId());
        return event;
    }
}
