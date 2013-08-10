package api.domain;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName(SmsEvent.EVENT_TYPE)
public class SmsEvent extends Event {

    public static final String EVENT_TYPE = "sms";

    @JsonProperty("sender")
    private String from;

    @JsonProperty("sms")
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
    public void accept(EventPersistingVisitor eventPersistingVisitor) {
        eventPersistingVisitor.visit(this);
    }
}
