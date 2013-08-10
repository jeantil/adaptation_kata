package factory;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import api.domain.AudioEvent;
import api.domain.Event;
import api.domain.FileEvent;
import api.domain.SmsEvent;
import converters.AudioConverter;
import converters.EventConverter;
import converters.FileConverter;
import converters.SmsConverter;
import persistent.EventEntity;
import persistent.UserEntity;
import support.CryptoService;

@Component
public class EventEntityFactory {

    private final HashMap<Class, EventConverter> converters;
    
    @Autowired
    public EventEntityFactory(CryptoService cryptoService) {
        this.converters = new HashMap<>();
        converters.put(FileEvent.class, new FileConverter(cryptoService));
        converters.put(AudioEvent.class, new AudioConverter(cryptoService));
        converters.put(SmsEvent.class, new SmsConverter());
    }
    

    public <T extends EventEntity, U extends Event> T toEntity (U model, UserEntity user){
        return (T) converters.get(model.getClass()).toEntity(model,user);
    }

}
