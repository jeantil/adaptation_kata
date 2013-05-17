package persistent;

import org.codehaus.jackson.annotate.JsonTypeName;
import api.domain.TextEvent;
import factory.EventModelFactory;

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

    @Override
    public TextEvent accept(EventModelFactory visitor) {
        //TODO we want to get rid of the cyclic dependency between the factory and the model
        return visitor.visit(this);
    }
}
