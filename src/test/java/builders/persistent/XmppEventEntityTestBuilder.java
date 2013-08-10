package builders.persistent;

import persistent.XmppEventEntity;

@SuppressWarnings("unchecked")
abstract class XmppEventEntityTestBuilder<T extends XmppEventEntityTestBuilder<T, U>,
        U extends XmppEventEntity> extends EventEntityTestBuilder<T,U>{

    private String xmppId;


    public T xmppId(String xmppId) {
        this.xmppId=xmppId;
        return (T) this;
    }

    void build(U eventEntity) {
        super.build(eventEntity);
        eventEntity.setXmppId(xmppId);
    }

}
