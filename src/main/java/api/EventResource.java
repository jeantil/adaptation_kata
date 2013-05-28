package api;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import api.domain.Event;
import api.domain.XmppEvent;
import services.EventStore;

//@Path("/events")
@Service
public class EventResource {
    private EventStore store;

    @Autowired
    public EventResource(EventStore store) {
        this.store = store;
    }

    //@POST
    public Event post(Event event){
        return null;
    }
    //@GET
    public Event get(UUID id){
        return null;
    }
    //@GET
    //@Path("/xmpp")
    public XmppEvent get(String xmppId){

        return null;
    }

}
