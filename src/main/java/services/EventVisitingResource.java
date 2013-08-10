package services;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import api.EventResource;
import api.domain.Event;
import api.domain.XmppEvent;
import factory.EventEntityFactory;
import factory.EventModelFactory;
import persistent.EventEntity;
import persistent.XmppEventEntity;

@Service
public class EventVisitingResource implements EventResource {

    private Session session;
    private EventStore store;

    private EventEntityFactory entityFactory;
    private EventModelFactory modelFactory;

    @Autowired
    public EventVisitingResource(Session session,EventStore store, EventEntityFactory entityFactory, EventModelFactory modelFactory) {
        this.session = session;
        this.store = store;
        this.entityFactory = entityFactory;
        this.modelFactory = modelFactory;
    }

    public Event post(Event event){
        return modelFactory.fromEntity(store.persist(entityFactory.toEntity(event,session.getUser())));
    }
    public Event get(UUID id){
        return modelFactory.fromEntity(store.findById(id, EventEntity.class));
    }

    public XmppEvent get(String xmppId){
        return modelFactory.fromEntity(store.findByXmppId(xmppId, XmppEventEntity.class));
    }


}
