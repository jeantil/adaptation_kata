package persistent;

import java.util.UUID;
import com.google.common.base.Objects;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import me.prettyprint.cassandra.utils.TimeUUIDUtils;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AudioEventEntity.class, name = AudioEventEntity.EVENT_TYPE),
        @JsonSubTypes.Type(value = FileEventEntity.class, name = FileEventEntity.EVENT_TYPE),
        @JsonSubTypes.Type(value = TextEventEntity.class, name = TextEventEntity.EVENT_TYPE),
        @JsonSubTypes.Type(value = SmsEventEntity.class, name = SmsEventEntity.EVENT_TYPE),
})
public abstract class EventEntity {


    private UUID id;

    private Long userId;

    @JsonIgnore
    public abstract EventType getType();

    @Override
    public String toString() {
        return Objects.toStringHelper(this) //
                .add("id", id) //
                .add("userId", userId) //
                .toString();
    }

    public void generateUniqueId() {
        setId(TimeUUIDUtils.getUniqueTimeUUIDinMillis());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
