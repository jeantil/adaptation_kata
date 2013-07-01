package factory;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import api.domain.Event;
import converters.AudioConverter;
import converters.EventConverter;
import converters.FileConverter;
import converters.SmsConverter;
import converters.TextConverter;
import persistent.AudioEventEntity;
import persistent.EventEntity;
import persistent.FileEventEntity;
import persistent.SmsEventEntity;
import persistent.TextEventEntity;
import services.URLService;

@Component
public class EventModelFactory {

    private final HashMap<Class, EventConverter> converters;

    @Autowired
    public EventModelFactory(URLService urlService) {
        this.converters = new HashMap<>();
        converters.put(FileEventEntity.class, new FileConverter(urlService));
        converters.put(AudioEventEntity.class, new AudioConverter(urlService));
        converters.put(SmsEventEntity.class, new SmsConverter());
        converters.put(TextEventEntity.class, new TextConverter());
    }

    public <T extends EventEntity, U extends Event> U fromEntity (T entity){
        return (U) converters.get(entity.getClass()).fromEntity(entity);
    }
}
