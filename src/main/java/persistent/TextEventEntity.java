package persistent;

import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName(TextEventEntity.EVENT_TYPE)
public class TextEventEntity extends XmppEventEntity {

    public static final String EVENT_TYPE = "text";

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public EventType getType() {
        return EventType.text;
    }

}
