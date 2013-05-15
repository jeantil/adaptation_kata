package api.domain;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

@XmlRootElement(name = "event")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({ AudioEvent.class, FileEvent.class,
         TextEvent.class, SmsEvent.class })
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = AudioEvent.class, name = AudioEvent.EVENT_TYPE),
        @JsonSubTypes.Type(value = FileEvent.class, name = FileEvent.EVENT_TYPE),
        @JsonSubTypes.Type(value = TextEvent.class, name = TextEvent.EVENT_TYPE),
        @JsonSubTypes.Type(value = SmsEvent.class, name = SmsEvent.EVENT_TYPE)})
public abstract class Event {

    @XmlElement(name = "id")
    @NotNull
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
