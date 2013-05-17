package converters;

import api.domain.XmppEvent;
import persistent.UserEntity;
import persistent.XmppEventEntity;

abstract class XmppEventConverter<T extends XmppEventEntity, U extends XmppEvent> extends EventConverter<T, U> {

    @Override
    T toEntity(T event, U model, UserEntity user) {
        final T eventEntity = super.toEntity(event, model, user);
        eventEntity.setXmppId(model.getXmppId());
        return eventEntity;
    }

    @Override
    U fromEntity(U model, T entity) {
        final U xmppModel = super.fromEntity(model, entity);
        xmppModel.setXmppId(entity.getXmppId());
        return xmppModel;
    }
}
