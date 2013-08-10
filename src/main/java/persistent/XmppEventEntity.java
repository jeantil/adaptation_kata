package persistent;

import api.domain.XmppEvent;

public abstract class XmppEventEntity extends EventEntity{

    private String xmppId;

    protected XmppEventEntity() {
        super();
    }

    public XmppEventEntity(XmppEvent xmppEvent, Long userId) {
        super(xmppEvent,userId);
        this.xmppId=xmppEvent.getXmppId();
    }

    public String getXmppId() {
        return xmppId;
    }

    public void setXmppId(String xmppId) {
        this.xmppId = xmppId;
    }

    @Override
    public EventType getType() {
        return EventType.sms;
    }
    public abstract XmppEvent accept(EventEntityVisitor visitor);
}
