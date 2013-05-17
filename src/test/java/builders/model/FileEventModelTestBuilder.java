package builders.model;


import api.event.domain.FileEvent;

public class FileEventModelTestBuilder extends XmppEventModelTestBuilder<FileEventModelTestBuilder, FileEvent> {

    private String filename;

    public static FileEventModelTestBuilder file() {
        return new FileEventModelTestBuilder();
    }

    public FileEvent build() {
        FileEvent fileModel = new FileEvent();
        super.build(fileModel);
        fileModel.setFilename(this.filename);
        return fileModel;
    }

    public FileEventModelTestBuilder filename(String filename) {
        this.filename = filename;
        return this;
    }
}
