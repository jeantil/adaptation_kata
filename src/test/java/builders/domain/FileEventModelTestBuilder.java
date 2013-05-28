package builders.domain;


import java.net.URL;
import api.domain.FileEvent;

public class FileEventModelTestBuilder extends XmppEventModelTestBuilder<FileEventModelTestBuilder, FileEvent> {

    private URL url;

    public static FileEventModelTestBuilder file() {
        return new FileEventModelTestBuilder();
    }

    public FileEvent build() {
        FileEvent fileModel = new FileEvent();
        super.build(fileModel);
        fileModel.setUrl(this.url);
        return fileModel;
    }

    public FileEventModelTestBuilder url(URL url) {
        this.url=url;
        return this;
    }
}
