package builders.model;


import api.event.domain.AudioEvent;
import api.event.domain.FileEvent;

public class AudioEventModelTestBuilder extends XmppEventModelTestBuilder<AudioEventModelTestBuilder, AudioEvent> {

    private String filename;
    private long duration;

    public static AudioEventModelTestBuilder audio() {
        return new AudioEventModelTestBuilder();
    }

    public AudioEvent build() {
        AudioEvent fileModel = new AudioEvent();
        super.build(fileModel);
        fileModel.setFilename(this.filename);
        fileModel.setDuration(this.duration);
        return fileModel;
    }

    public AudioEventModelTestBuilder filename(String filename) {
        this.filename = filename;
        return this;
    }
    public AudioEventModelTestBuilder duration(long duration) {
        this.duration=duration;
        return this;
    }
}
