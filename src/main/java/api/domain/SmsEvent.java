package api.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonTypeName;

@XmlRootElement(name = "event-sms")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonTypeName(SmsEvent.EVENT_TYPE)
public class SmsEvent extends Event {

    public static final String EVENT_TYPE = "sms";

    @XmlElement(name = "interlocutor-msisdn")
    private String from;

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public <E, U> E accept(EventVisitor<E, U> visitor, U user) {
        return visitor.visit(this, user);
    }
}
