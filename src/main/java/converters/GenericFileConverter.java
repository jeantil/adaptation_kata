package converters;

import api.domain.FileEvent;
import persistent.FileEventEntity;
import persistent.UserEntity;
import support.CryptoService;

abstract class GenericFileConverter<T extends FileEventEntity,U extends FileEvent> extends XmppEventConverter<T,
        U> {
    private final CryptoService cryptoService;

    public GenericFileConverter(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @Override
    protected T toEntity(T event, U model, UserEntity user) {
        final T file = super.toEntity(event, model, user);
        file.setCryptoHash(cryptoService.toCryptoHash(model.getFilename()));
        return file;
    }

    @Override
    U fromEntity(U model, T entity) {
        final U fileModel = super.fromEntity(model, entity);
        fileModel.setFilename(cryptoService.fromCryptoHash(entity.getCryptoHash()));
        return fileModel;
    }
}
