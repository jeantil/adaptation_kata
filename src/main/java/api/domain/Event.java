package api.domain;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = AudioEvent.class, name = AudioEvent.EVENT_TYPE),
        @JsonSubTypes.Type(value = FileEvent.class, name = FileEvent.EVENT_TYPE),
        @JsonSubTypes.Type(value = SmsEvent.class, name = SmsEvent.EVENT_TYPE)})
public abstract class Event {

    @NotNull
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public abstract void accept(EventPersistingVisitor eventPersistingVisitor);
}
