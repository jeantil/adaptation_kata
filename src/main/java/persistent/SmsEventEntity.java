package persistent;

import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName(SmsEventEntity.EVENT_TYPE)
public class SmsEventEntity extends EventEntity {

    public static final String EVENT_TYPE = "sms";

    private String from;

    private String text;

    public SmsEventEntity() {
        super();
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public EventType getType() {
        return EventType.sms;
    }

}
