package services;

import java.util.UUID;

import api.domain.*;
import fr.xebia.extras.selma.Selma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import api.EventResource;
import persistent.*;

@Service
public class EventVisitingResource implements EventResource {

    private final EntityToResourceMapper entityToResource;
    private Session session;
    private final EventStore store;

    @Autowired
    public EventVisitingResource(Session session, EventStore store, EntityToResourceMapper entityToResource) {
        this.session = session;
        this.store = store;
        this.entityToResource = entityToResource;
    }

    @Override
    public Event post(Event event){
        UserEntity user = session.getUser();
        EventEntity entity = entityToResource.asEventEntity(event, user);

        return entityToResource.asResource(store.persist(entity));
    }



    @Override
    public Event get(UUID id){
        EventEntity eventEntity = store.findById(id, EventEntity.class);

        Event res = entityToResource.asResource(eventEntity);
        return res;
    }



    @Override
    public XmppEvent get(String xmppId){

        XmppEventEntity eventEntity = store.findByXmppId(xmppId, XmppEventEntity.class);

        return asXmppEvent(eventEntity);
    }

    private XmppEvent asXmppEvent(XmppEventEntity eventEntity) {
        return (XmppEvent)entityToResource.asResource(eventEntity);
    }

}
