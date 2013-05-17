package api.event.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonTypeName;

@XmlRootElement(name = "event-file")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonTypeName(FileEvent.EVENT_TYPE)
public class FileEvent extends XmppEvent {

    public static final String EVENT_TYPE = "file";

    private String filename;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public <E, U> E accept(EventVisitor<E, U> visitor, U user) {
        return visitor.visit(this, user);
    }
}
