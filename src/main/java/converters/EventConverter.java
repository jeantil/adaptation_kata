package converters;

import api.domain.Event;
import persistent.EventEntity;
import persistent.UserEntity;

public abstract class EventConverter<T extends EventEntity, U extends Event> {

    public abstract T toEntity(U model, UserEntity user);

    T toEntity(T event, U model, UserEntity user) {
        event.setId(model.getId());
        event.setUserId(user.getUserId());
        return event;
    }
}
