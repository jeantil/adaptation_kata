package builders.model;

import api.domain.XmppEvent;

public abstract class XmppEventModelTestBuilder<T extends XmppEventModelTestBuilder<T, U>, U extends XmppEvent>
        extends EventModelTestBuilder<T, U> {

    private String xmppId;

    void build(U eventModelOn) {
        super.build(eventModelOn);
        eventModelOn.setXmppId(xmppId);
    }

    public T xmppId(String xmppId) {
        this.xmppId = xmppId;
        return (T) this;
    }
}
