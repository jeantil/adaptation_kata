package builders.persistent;

import persistent.FileEventEntity;

public class FileEventEntityTestBuilder extends XmppEventEntityTestBuilder<FileEventEntityTestBuilder,
        FileEventEntity> {

    private String filename;

    public static FileEventEntityTestBuilder file() {
        return new FileEventEntityTestBuilder();
    }

    public FileEventEntity build() {
        FileEventEntity textEntity = new FileEventEntity();
        super.build(textEntity);

        textEntity.setFilename(filename);

        return textEntity;
    }

    public FileEventEntityTestBuilder filename(String filename) {
        this.filename = filename;
        return this;
    }

}
