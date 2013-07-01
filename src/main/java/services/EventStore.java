package services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.UUID;
import com.google.common.collect.Maps;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import persistent.EventEntity;
import persistent.XmppEventEntity;

/**
 * A better implementation of the EventStore would store json representations of the events,
 * as defined by the mapping of the persistent package. If you decide to unify the hierarchies,
 * you _must_ first implement this as a "real" store of json and ensure backward compatibility
 * with the store by whichever way you see fit.
 */
@Component
public class EventStore {
    static final Logger logger = LoggerFactory.getLogger(EventStore.class);
    private Map<UUID, EventEntity> events= Maps.newHashMap();
    private ObjectMapper mapper;
    private String storePath;

    @Autowired
    public EventStore(ObjectMapper mapper, @Value("${store.path}") String storePath) {
        this.mapper = mapper;
        this.storePath = storePath;
    }

    public<T extends EventEntity> T persist(T event){
        try {
            if(event.getId()==null) event.setId(UUID.randomUUID());
            mapper.writeValue(asFile(event.getId()), event);
        } catch (IOException e) {
            logger.info("error",e);
        }
        return event;
    }

    private <T extends EventEntity> File asFile(UUID id) {
        return new File(storePath+File.separator+id+".json");
    }

    public <T extends EventEntity> T findById(UUID id, Class<T> klass){
        try {
            final T t = mapper.readValue(asFile(id), klass);
            return t;
        } catch (IOException e) {
            logger.info("errror",e);
        }
        return null;
    }

    public<T extends XmppEventEntity> T findByXmppId(String id,Class<T> klass){
        final File[] files = new File(storePath).listFiles();
        for (File file : files) {
            try {
                final T t = mapper.readValue(file, klass);
                if(t instanceof XmppEventEntity && id.equals(((XmppEventEntity) t).getXmppId())){
                    return t;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
}
