package factory;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import api.domain.AudioEvent;
import api.domain.Event;
import api.domain.FileEvent;
import api.domain.SmsEvent;
import api.domain.TextEvent;
import converters.AudioConverter;
import converters.EventConverter;
import converters.FileConverter;
import converters.SmsConverter;
import converters.TextConverter;
import persistent.EventEntity;
import persistent.UserEntity;
import services.URLService;

@Component
public class EventEntityFactory {

    private final HashMap<Class, EventConverter> converters;
    
    @Autowired
    public EventEntityFactory(URLService urlService) {
        this.converters = new HashMap<>();
        converters.put(FileEvent.class, new FileConverter(urlService));
        converters.put(AudioEvent.class, new AudioConverter(urlService));
        converters.put(SmsEvent.class, new SmsConverter());
        converters.put(TextEvent.class, new TextConverter());
    }
    

    public <T extends EventEntity, U extends Event> T toEntity (U model, UserEntity user){
        return (T) converters.get(model.getClass()).toEntity(model,user);
    }

}
