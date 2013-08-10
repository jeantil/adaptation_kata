package services;

import api.domain.AudioEvent;
import api.domain.EventPersistingVisitor;
import api.domain.FileEvent;
import api.domain.SmsEvent;
import persistent.AudioEventEntity;
import persistent.FileEventEntity;
import persistent.SmsEventEntity;
import persistent.UserEntity;
import support.CryptoService;

public class EventPersistingVisitorImpl implements EventPersistingVisitor{
    private final EventStore store;
    private final CryptoService cryptoService;
    private final UserEntity user;

    public EventPersistingVisitorImpl(EventStore store, CryptoService cryptoService, UserEntity user) {
        this.store = store;
        this.cryptoService = cryptoService;
        this.user = user;
    }

    public void visit(SmsEvent smsEvent) {
        SmsEventEntity smsEventEntity = new SmsEventEntity(smsEvent,user.getUserId());
        smsEventEntity=store.persist(smsEventEntity);
        smsEvent.setId(smsEventEntity.getId());
    }

    public void visit(FileEvent fileEvent) {
        FileEventEntity fileEventEntity = new FileEventEntity(fileEvent, cryptoService.toCryptoHash(fileEvent.getFilename()), user.getUserId());
        fileEventEntity = store.persist(fileEventEntity);
        fileEvent.setId(fileEventEntity.getId());
    }


    public void visit(AudioEvent audioEvent) {
        AudioEventEntity audioEventEntity = new AudioEventEntity(audioEvent, cryptoService.toCryptoHash(audioEvent.getFilename()), user.getUserId());
        audioEventEntity = store.persist(audioEventEntity);
        audioEvent.setId(audioEventEntity.getId());
    }
}
