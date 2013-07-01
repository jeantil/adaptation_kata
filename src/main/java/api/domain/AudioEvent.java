package api.domain;

import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName(AudioEvent.EVENT_TYPE)
public class AudioEvent extends FileEvent {

    public static final String EVENT_TYPE = "audio";

    private long duration;

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
