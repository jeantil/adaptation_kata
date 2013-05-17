package persistent;

import api.event.domain.Event;
import factory.EventModelFactory;

public abstract class XmppEventEntity extends EventEntity{

    private String xmppId;

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

    public abstract Event accept(EventModelFactory visitor);

}
