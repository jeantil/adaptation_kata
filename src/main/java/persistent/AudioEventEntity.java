package persistent;

import org.codehaus.jackson.annotate.JsonTypeName;
import api.domain.AudioEvent;
import persistent.support.CryptoHash;


@JsonTypeName(AudioEventEntity.EVENT_TYPE)
public class AudioEventEntity extends FileEventEntity {
    public static final String EVENT_TYPE = "audio";

    private long duration;


    public AudioEventEntity(AudioEvent audioEvent, CryptoHash cryptoHash, Long userId) {
        super(audioEvent, cryptoHash, userId);
        this.duration = audioEvent.getDuration();
    }

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

    @Override
    public AudioEvent accept(EventEntityVisitor eventEntityVisitor) {
        return eventEntityVisitor.visit(this);
    }

}
