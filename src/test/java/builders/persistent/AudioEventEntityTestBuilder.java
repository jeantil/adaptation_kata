package builders.persistent;

import persistent.AudioEventEntity;
import persistent.FileEventEntity;

public class AudioEventEntityTestBuilder extends XmppEventEntityTestBuilder<AudioEventEntityTestBuilder,
        AudioEventEntity> {

    private String filename;
    private long duration;

    public static AudioEventEntityTestBuilder audio() {
        return new AudioEventEntityTestBuilder();
    }

    public AudioEventEntity build() {
        AudioEventEntity entity = new AudioEventEntity();
        super.build(entity);

        entity.setFilename(filename);
        entity.setDuration(duration);
        return entity;
    }

    public AudioEventEntityTestBuilder filename(String filename) {
        this.filename = filename;
        return this;
    }
    public AudioEventEntityTestBuilder duration(long duration) {
        this.duration=duration;
        return this;
    }
}
