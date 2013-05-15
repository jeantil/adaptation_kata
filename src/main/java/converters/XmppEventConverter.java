package converters;

import api.domain.XmppEvent;
import persistent.UserEntity;
import persistent.XmppEventEntity;

public abstract class XmppEventConverter<T extends XmppEventEntity, U extends XmppEvent> extends EventConverter<T, U> {

    @Override
    protected T toEntity(T event, U model, UserEntity user) {
        final T eventEntity = super.toEntity(event, model, user);
        eventEntity.setXmppId(model.getXmppId());
        return eventEntity;
    }
}
