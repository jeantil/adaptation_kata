package api.event.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonTypeName;

@XmlRootElement(name = "event-text")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonTypeName(TextEvent.EVENT_TYPE)
public class TextEvent extends XmppEvent {

    public static final String EVENT_TYPE = "text";

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public <E, U> E accept(EventVisitor<E, U> visitor, U user) {
        return visitor.visit(this, user);
    }
}
