package converters;

import api.event.domain.FileEvent;
import persistent.FileEventEntity;
import persistent.UserEntity;
import services.CryptoService;

public abstract class GenericFileConverter<T extends FileEventEntity,U extends FileEvent> extends XmppEventConverter<T,
        U> {
    private CryptoService cryptoService;

    public GenericFileConverter(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @Override
    protected T toEntity(T event, U model, UserEntity user) {
        final T file = super.toEntity(event, model, user);
        file.setFilename(cryptoService.cryptFilename(model.getFilename()));
        return file;
    }
}
