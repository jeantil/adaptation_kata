package api;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import api.domain.Event;
import api.domain.XmppEvent;
import factory.EventEntityFactory;
import factory.EventModelFactory;
import persistent.EventEntity;
import persistent.UserEntity;
import persistent.XmppEventEntity;
import services.EventStore;

//@Path("/events")
@Service
public class EventResource {
    private EventStore store;

    private EventEntityFactory entityFactory;
    private EventModelFactory modelFactory;

    @Autowired
    public EventResource(EventStore store, EventEntityFactory entityFactory, EventModelFactory modelFactory) {
        this.store = store;
        this.entityFactory = entityFactory;
        this.modelFactory = modelFactory;
    }

    //@POST
    public Event post(Event event){
        return modelFactory.fromEntity(store.persist(entityFactory.toEntity(event,new UserEntity(1l))));
    }
    //@GET
    public Event get(UUID id){
        return modelFactory.fromEntity(store.findById(id, EventEntity.class));
    }
    //@GET
    //@Path("/xmpp")
    public XmppEvent get(String xmppId){
        return modelFactory.fromEntity(store.findByXmppId(xmppId, XmppEventEntity.class));
    }

}
