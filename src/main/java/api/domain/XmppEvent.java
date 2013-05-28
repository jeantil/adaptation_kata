package api.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class XmppEvent extends Event {

    private String xmppId;

    public String getXmppId() {
        return xmppId;
    }

    public void setXmppId(String xmppId) {
        this.xmppId = xmppId;
    }

}
