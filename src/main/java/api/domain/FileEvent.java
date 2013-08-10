package api.domain;

import org.codehaus.jackson.annotate.JsonTypeName;

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
}
