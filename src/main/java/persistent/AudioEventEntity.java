package persistent;

import org.codehaus.jackson.annotate.JsonTypeName;
import api.domain.FileEvent;
import factory.EventModelFactory;

@JsonTypeName(AudioEventEntity.EVENT_TYPE)
public class AudioEventEntity extends FileEventEntity {
    public static final String EVENT_TYPE = "audio";

    private long duration;

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public EventType getType() {
        return EventType.audio;
    }

    @Override
    public FileEvent accept(EventModelFactory visitor) {
        //TODO we want to get rid of the cyclic dependency between the factory and the model
        return visitor.fromEntity(this);
    }
}
