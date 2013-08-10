package services;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import api.EventResource;
import api.domain.Event;
import api.domain.XmppEvent;
import persistent.UserEntity;

@Service
public class EventVisitingResource implements EventResource {

    private Session session;

    @Autowired
    public EventVisitingResource(Session session) {
        this.session = session;
    }

    @Override
    public Event post(Event event){
        UserEntity user = session.getUser();
        return null;
    }

    @Override
    public Event get(UUID id){
        return null;
    }

    @Override
    public XmppEvent get(String xmppId){
        return null;
    }

}
