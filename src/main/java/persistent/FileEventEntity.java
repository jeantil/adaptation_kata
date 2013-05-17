package persistent;

import org.codehaus.jackson.annotate.JsonTypeName;
import api.domain.FileEvent;
import factory.EventModelFactory;

@JsonTypeName(FileEventEntity.EVENT_TYPE)
public class FileEventEntity extends XmppEventEntity {

    public static final String EVENT_TYPE = "file";

    private String filename;

    @Override
    public EventType getType() {
        return EventType.file;
    }

    @Override
    public FileEvent accept(EventModelFactory visitor) {
        //TODO we want to get rid of the cyclic dependency between the factory and the model
        return visitor.visit(this);
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
