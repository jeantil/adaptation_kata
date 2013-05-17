package api.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonTypeName;

@XmlRootElement(name = "event-audio")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonTypeName(FileEvent.EVENT_TYPE)
public class AudioEvent extends FileEvent {

    public static final String EVENT_TYPE = "audio";

    private long duration;

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public <E, U> E accept(EventVisitor<E, U> visitor, U user) {
        return visitor.visit(this, user);
    }
}
