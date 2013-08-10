package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import api.domain.AudioEvent;
import api.domain.Event;
import api.domain.FileEvent;
import api.domain.SmsEvent;
import persistent.AudioEventEntity;
import persistent.EventEntity;
import persistent.EventEntityVisitor;
import persistent.FileEventEntity;
import persistent.SmsEventEntity;
import support.CryptoService;

@Component
public class EventEntityVisitorImpl implements EventEntityVisitor {

    private final CryptoService cryptoService;

    @Autowired
    public EventEntityVisitorImpl(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @Override
    public FileEvent visit(FileEventEntity fileEventEntity) {
        FileEvent fileEvent = new FileEvent();
        fileEventEntityToFileEvent(fileEventEntity, fileEvent);
        return fileEvent;
    }

    private void fileEventEntityToFileEvent(FileEventEntity fileEventEntity, FileEvent fileEvent) {
        eventEntityToEvent(fileEventEntity, fileEvent);
        fileEvent.setFilename(cryptoService.fromCryptoHash(fileEventEntity.getCryptoHash()));
        fileEvent.setXmppId(fileEventEntity.getXmppId());
    }

    private void eventEntityToEvent(EventEntity fileEventEntity, Event fileEvent) {
        fileEvent.setId(fileEventEntity.getId());
    }

    @Override
    public AudioEvent visit(AudioEventEntity audioEventEntity) {
        AudioEvent audioEvent = new AudioEvent();
        fileEventEntityToFileEvent(audioEventEntity,audioEvent);
        audioEvent.setDuration(audioEventEntity.getDuration());
        return audioEvent;
    }

    @Override
    public SmsEvent visit(SmsEventEntity smsEventEntity) {
        SmsEvent smsEvent = new SmsEvent();
        eventEntityToEvent(smsEventEntity,smsEvent);
        smsEvent.setFrom(smsEventEntity.getFrom());
        smsEvent.setText(smsEventEntity.getText());
        return smsEvent;
    }
}
