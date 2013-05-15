package converters;

import api.event.domain.AudioEvent;
import persistent.AudioEventEntity;
import persistent.UserEntity;
import services.CryptoService;

public class AudioConverter extends GenericFileConverter<AudioEventEntity,AudioEvent>{

    public AudioConverter(CryptoService cryptoService) {
        super(cryptoService);
    }

    public AudioEventEntity toEntity(AudioEvent model, UserEntity userEntity) {
        final AudioEventEntity audio = super.toEntity(new AudioEventEntity(), model, userEntity);
        audio.setDuration(model.getDuration());
        return audio;
    }
}
