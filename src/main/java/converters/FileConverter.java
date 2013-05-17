package converters;

import api.domain.FileEvent;
import persistent.FileEventEntity;
import persistent.UserEntity;
import services.URLService;

public class FileConverter extends GenericFileConverter<FileEventEntity,FileEvent> {

    public FileConverter(URLService cryptoService) {
        super(cryptoService);
    }

    @Override
    public FileEventEntity toEntity(FileEvent model, UserEntity user) {
        return super.toEntity(new FileEventEntity(), model,user);
    }

    @Override
    public FileEvent fromEntity(FileEventEntity entity) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
