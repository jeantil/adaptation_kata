package factory;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import api.domain.Event;
import converters.AudioConverter;
import converters.EventConverter;
import converters.FileConverter;
import converters.SmsConverter;
import persistent.AudioEventEntity;
import persistent.EventEntity;
import persistent.FileEventEntity;
import persistent.SmsEventEntity;
import support.CryptoService;

@Component
public class EventModelFactory {

    private final HashMap<Class, EventConverter> converters;

    @Autowired
    public EventModelFactory(CryptoService cryptoService) {
        this.converters = new HashMap<>();
        converters.put(FileEventEntity.class, new FileConverter(cryptoService));
        converters.put(AudioEventEntity.class, new AudioConverter(cryptoService));
        converters.put(SmsEventEntity.class, new SmsConverter());
    }

    public <T extends EventEntity, U extends Event> U fromEntity (T entity){
        return (U) converters.get(entity.getClass()).fromEntity(entity);
    }
}
