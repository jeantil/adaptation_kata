package api.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonTypeName;

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
}
