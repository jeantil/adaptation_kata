package api;

import java.util.UUID;
import api.domain.Event;
import api.domain.XmppEvent;

public interface EventResource {
    Event post(Event event);

    Event get(UUID id);

    XmppEvent get(String xmppId);
}
