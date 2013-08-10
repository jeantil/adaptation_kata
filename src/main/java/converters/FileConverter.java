package converters;

import api.domain.FileEvent;
import persistent.FileEventEntity;
import persistent.UserEntity;
import support.CryptoService;

public class FileConverter extends GenericFileConverter<FileEventEntity,FileEvent> {

    public FileConverter(CryptoService cryptoService) {
        super(cryptoService);
    }

    @Override
    public FileEventEntity toEntity(FileEvent model, UserEntity user) {
        return super.toEntity(new FileEventEntity(), model,user);
    }

    @Override
    public FileEvent fromEntity(FileEventEntity entity) {
        return super.fromEntity(new FileEvent(), entity);

    }
}
