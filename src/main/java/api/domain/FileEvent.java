package api.domain;

import java.net.URL;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonTypeName;

@XmlRootElement(name = "event-file")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonTypeName(FileEvent.EVENT_TYPE)
public class FileEvent extends XmppEvent {

    public static final String EVENT_TYPE = "file";

    private URL url;

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
