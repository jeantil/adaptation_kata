package converters;

import api.event.domain.FileEvent;
import persistent.FileEventEntity;
import persistent.UserEntity;
import services.CryptoService;

public class FileConverter extends EventConverter {
    private CryptoService cryptoService;

    public FileConverter(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }
    public FileEventEntity toEntity(FileEvent model, UserEntity userEntity) {
        final FileEventEntity file = super.toEntity(new FileEventEntity(), model, userEntity);

        file.setXmppId(model.getXmppId());
        file.setFilename(model.getFilename());

        return file;
    }
}
