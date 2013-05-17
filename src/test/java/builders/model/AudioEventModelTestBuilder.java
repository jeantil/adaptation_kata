package builders.model;


import java.net.URL;
import api.domain.AudioEvent;

public class AudioEventModelTestBuilder extends XmppEventModelTestBuilder<AudioEventModelTestBuilder, AudioEvent> {

    private URL url;
    private long duration;

    public static AudioEventModelTestBuilder audio() {
        return new AudioEventModelTestBuilder();
    }

    public AudioEvent build() {
        AudioEvent fileModel = new AudioEvent();
        super.build(fileModel);
        fileModel.setUrl(this.url);
        fileModel.setDuration(this.duration);
        return fileModel;
    }

    public AudioEventModelTestBuilder url(URL url) {
        this.url = url;
        return this;
    }
    public AudioEventModelTestBuilder duration(long duration) {
        this.duration=duration;
        return this;
    }
}
