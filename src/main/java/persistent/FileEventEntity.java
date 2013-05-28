package persistent;

import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName(FileEventEntity.EVENT_TYPE)
public class FileEventEntity extends XmppEventEntity {

    public static final String EVENT_TYPE = "file";

    private String filename;

    @Override
    public EventType getType() {
        return EventType.file;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
