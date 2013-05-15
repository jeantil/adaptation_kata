package converters;

import api.domain.FileEvent;
import persistent.FileEventEntity;
import persistent.UserEntity;
import services.URLService;

abstract class GenericFileConverter<T extends FileEventEntity,U extends FileEvent> extends XmppEventConverter<T,
        U> {
    private final URLService urlService;

    public GenericFileConverter(URLService urlService) {
        this.urlService = urlService;
    }

    @Override
    protected T toEntity(T event, U model, UserEntity user) {
        final T file = super.toEntity(event, model, user);
        file.setFilename(urlService.fromUrl(model.getUrl()));
        return file;
    }
}
