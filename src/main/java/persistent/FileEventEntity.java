package persistent;

import org.codehaus.jackson.annotate.JsonTypeName;
import api.domain.FileEvent;
import persistent.support.CryptoHash;

@JsonTypeName(FileEventEntity.EVENT_TYPE)
public class FileEventEntity extends XmppEventEntity {

    public static final String EVENT_TYPE = "file";

    private CryptoHash cryptoHash;

    public FileEventEntity() {
        super();
    }

    public FileEventEntity(FileEvent fileEvent, CryptoHash cryptoHash, Long userId) {
        super(fileEvent, userId);
        this.cryptoHash = cryptoHash;
    }

    @Override
    public EventType getType() {
        return EventType.file;
    }

    @Override
    public FileEvent accept(EventEntityVisitor eventEntityVisitor) {
        return eventEntityVisitor.visit(this);
    }

    public CryptoHash getCryptoHash() {
        return cryptoHash;
    }

    public void setCryptoHash(CryptoHash cryptoHash) {
        this.cryptoHash = cryptoHash;
    }
}
