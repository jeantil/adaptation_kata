package persistent;

import org.codehaus.jackson.annotate.JsonTypeName;
import api.domain.SmsEvent;

@JsonTypeName(SmsEventEntity.EVENT_TYPE)
public class SmsEventEntity extends EventEntity {

    public static final String EVENT_TYPE = "sms";

    private String from;

    private String text;

    public SmsEventEntity() {
        super();
    }

    public SmsEventEntity(SmsEvent smsEvent, Long userId) {
        super(smsEvent,userId);
        from=smsEvent.getFrom();
        text=smsEvent.getText();
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

    @Override
    public SmsEvent accept(EventEntityVisitor eventEntityVisitor) {
        return eventEntityVisitor.visit(this);
    }

}
