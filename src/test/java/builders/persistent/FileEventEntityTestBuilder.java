package builders.persistent;

import persistent.FileEventEntity;
import persistent.support.CryptoAlgo;
import persistent.support.CryptoHash;

public class FileEventEntityTestBuilder extends XmppEventEntityTestBuilder<FileEventEntityTestBuilder,
        FileEventEntity> {

    private String hash;

    public static FileEventEntityTestBuilder file() {
        return new FileEventEntityTestBuilder();
    }

    public FileEventEntity build() {
        FileEventEntity fileEntity = new FileEventEntity();
        super.build(fileEntity);

        fileEntity.setCryptoHash(new CryptoHash(hash, CryptoAlgo.ULTIMATE_42 ));

        return fileEntity;
    }

    public FileEventEntityTestBuilder hash(String hash) {
        this.hash = hash;
        return this;
    }

}
