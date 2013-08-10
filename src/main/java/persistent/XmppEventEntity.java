package persistent;

public abstract class XmppEventEntity extends EventEntity{

    private String xmppId;

    protected XmppEventEntity() {
        super();
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
}
