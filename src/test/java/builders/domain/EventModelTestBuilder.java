package builders.domain;

import java.util.UUID;
import api.domain.Event;

@SuppressWarnings("unchecked")
public abstract class EventModelTestBuilder<T extends EventModelTestBuilder<T, U>, U extends Event> {

    private UUID serverId;

    void build(Event event) {
        event.setId(serverId);
    }

    public T serverId(UUID serverId) {
        this.serverId = serverId;
        return (T) this;
    }
}
