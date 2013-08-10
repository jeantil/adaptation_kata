package builders.persistent;

import persistent.AudioEventEntity;
import persistent.support.CryptoAlgo;
import persistent.support.CryptoHash;

public class AudioEventEntityTestBuilder extends XmppEventEntityTestBuilder<AudioEventEntityTestBuilder,
        AudioEventEntity> {

    private String hash;
    private long duration;

    public static AudioEventEntityTestBuilder audio() {
        return new AudioEventEntityTestBuilder();
    }

    public AudioEventEntity build() {
        AudioEventEntity entity = new AudioEventEntity();
        super.build(entity);
        if(hash!=null){
            entity.setCryptoHash(new CryptoHash(hash, CryptoAlgo.ULTIMATE_42));
        }
        entity.setDuration(duration);
        return entity;
    }

    public AudioEventEntityTestBuilder hash(String hash) {
        this.hash = hash;
        return this;
    }
    public AudioEventEntityTestBuilder duration(long duration) {
        this.duration=duration;
        return this;
    }
}
