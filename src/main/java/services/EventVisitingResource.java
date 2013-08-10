package services;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import api.EventResource;
import api.domain.Event;
import api.domain.EventPersistingVisitor;
import api.domain.XmppEvent;
import persistent.EventEntity;
import persistent.EventEntityVisitor;
import persistent.XmppEventEntity;
import support.CryptoService;

@Service
public class EventVisitingResource implements EventResource {
    private final EventStore store;
    private final Session session;
    private final CryptoService cryptoService;

    @Autowired
    public EventVisitingResource(EventStore store, Session session, CryptoService cryptoService) {
        this.store = store;
        this.cryptoService = cryptoService;
        this.session = session;
    }

    @Override
    public Event post(Event event){
        EventPersistingVisitor eventPersistingVisitor=new EventPersistingVisitorImpl(store,cryptoService,session.getUser());
        event.accept(eventPersistingVisitor);
        return event;
    }

    @Override
    public Event get(UUID id){
        EventEntity entity = store.findById(id, EventEntity.class);
        EventEntityVisitor eventEntityVisitor=new EventEntityVisitorImpl(cryptoService);
        return entity.accept(eventEntityVisitor);
    }

    @Override
    public XmppEvent get(String xmppId){
        EventEntityVisitor eventEntityVisitor=new EventEntityVisitorImpl(cryptoService);
        XmppEventEntity entity = store.findByXmppId(xmppId, XmppEventEntity.class);
        return entity.accept(eventEntityVisitor);
    }

}
