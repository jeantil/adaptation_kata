package services;

import java.util.Map;
import java.util.UUID;
import com.google.common.collect.Maps;
import persistent.EventEntity;
import persistent.XmppEventEntity;

/**
 * A better implementation of the EventStore would store json representations of the events,
 * as defined by the mapping of the persistent package. If you decide to unify the hierarchies,
 * you _must_ first implement this as a "real" store of json and ensure backward compatibility
 * with the store by whichever way you see fit.
 */
public class EventStore {
    private Map<UUID, EventEntity> events= Maps.newHashMap();

    public<T extends EventEntity> T persist(T event){
        events.put(event.getId(), event);
        return event;
    }

    public<T extends EventEntity> T findById(UUID id){
        return (T) events.get(id);
    }

    public<T extends XmppEventEntity> T findByXmppId(String id){
        for (EventEntity eventEntity : events.values()) {
            if(eventEntity instanceof XmppEventEntity && id.equals(((XmppEventEntity) eventEntity).getXmppId())){
                return (T) eventEntity;
            }
        }
        return null;
    }
}
