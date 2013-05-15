package converters;

import api.event.domain.FileEvent;
import persistent.FileEventEntity;
import persistent.UserEntity;
import services.CryptoService;

public class FileConverter extends GenericFileConverter<FileEventEntity,FileEvent> {

    public FileConverter(CryptoService cryptoService) {
        super(cryptoService);
    }

    @Override
    public FileEventEntity toEntity(FileEvent model, UserEntity user) {
        return super.toEntity(new FileEventEntity(), model,user);
    }
}
