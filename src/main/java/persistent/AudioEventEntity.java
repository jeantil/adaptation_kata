package persistent;

import org.codehaus.jackson.annotate.JsonTypeName;


@JsonTypeName(AudioEventEntity.EVENT_TYPE)
public class AudioEventEntity extends FileEventEntity {
    public static final String EVENT_TYPE = "audio";

    private long duration;

    public AudioEventEntity() {
        super();
    }

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
}
