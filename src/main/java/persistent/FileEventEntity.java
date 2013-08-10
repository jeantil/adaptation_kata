package persistent;

import org.codehaus.jackson.annotate.JsonTypeName;
import persistent.support.CryptoHash;

@JsonTypeName(FileEventEntity.EVENT_TYPE)
public class FileEventEntity extends XmppEventEntity {

    public static final String EVENT_TYPE = "file";

    private CryptoHash cryptoHash;

    public FileEventEntity() {
        super();
    }

    @Override
    public EventType getType() {
        return EventType.file;
    }

    public CryptoHash getCryptoHash() {
        return cryptoHash;
    }

    public void setCryptoHash(CryptoHash cryptoHash) {
        this.cryptoHash = cryptoHash;
    }
}
