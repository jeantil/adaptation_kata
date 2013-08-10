package builders.persistent;

import java.util.UUID;
import org.apache.commons.lang.math.RandomUtils;
import persistent.EventEntity;

@SuppressWarnings("unchecked")
abstract class EventEntityTestBuilder<T extends EventEntityTestBuilder<T, U>, U extends EventEntity> {

    private Long userId;
    private UUID id;

    public T randomUserId() {
        this.userId = (long) RandomUtils.nextInt(100000000);
        return (T) this;
    }

    public T id(UUID id) {
        this.id = id;
        return (T) this;
    }

    public T userId(Long userId) {
        this.userId = userId;
        return (T) this;
    }

    void build(U eventEntity) {
        if (id == null) {
            eventEntity.generateUniqueId();
        } else {
            eventEntity.setId(id);
        }

        eventEntity.setUserId(userId);
    }

}
