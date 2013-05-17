package factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import api.event.domain.AudioEvent;
import api.event.domain.Event;
import api.event.domain.EventVisitor;
import api.event.domain.FileEvent;
import api.event.domain.SmsEvent;
import api.event.domain.TextEvent;
import api.event.domain.XmppEvent;
import persistent.AudioEventEntity;
import persistent.EventEntity;
import persistent.FileEventEntity;
import persistent.SmsEventEntity;
import persistent.TextEventEntity;
import persistent.XmppEventEntity;
import persistent.UserEntity;
import services.CryptoService;

@Component
public class EventEntityFactory implements EventVisitor<EventEntity, UserEntity> {

    @Autowired
    private CryptoService cryptoService;

    private void mapBaseProperties(EventEntity entity, UserEntity user, Event model) {

        entity.setId(model.getId());
        entity.setUserId(user.getUserId());
    }

    private void mapXmppProperties(XmppEventEntity entity, UserEntity user, XmppEvent model) {
        this.mapBaseProperties(entity, user, model);
        entity.setXmppId(model.getXmppId());
    }

    private void mapAudioProperties(AudioEventEntity entity, UserEntity user, AudioEvent model) {
        this.mapFileProperties(entity, user, model);

        this.fillAudioDuration(entity, model);
    }

    private void mapFileProperties(FileEventEntity entity, UserEntity user, FileEvent model) {
        this.mapXmppProperties(entity, user, model);

        entity.setFilename(cryptoService.cryptFilename(model.getFilename()));
    }


    @Override
    public FileEventEntity visit(FileEvent model, UserEntity user) {
        FileEventEntity entity = new FileEventEntity();

        this.mapFileProperties(entity, user, model);

        return entity;
    }

    @Override
    public AudioEventEntity visit(AudioEvent model, UserEntity user) {
        AudioEventEntity entity = new AudioEventEntity();

        this.mapAudioProperties(entity, user, model);

        return entity;
    }

    @Override
    public SmsEventEntity visit(SmsEvent model, UserEntity user) {
        SmsEventEntity entity = new SmsEventEntity();

        this.mapBaseProperties(entity, user, model);

        entity.setFrom(model.getFrom());
        entity.setText(model.getText());

        return entity;

    }


    @Override
    public TextEventEntity visit(TextEvent model, UserEntity user) {
        TextEventEntity entity = new TextEventEntity();
    
        this.mapXmppProperties(entity, user, model);
        entity.setXmppId(model.getXmppId());
        entity.setText(model.getText());
    
        return entity;
    }

    private void fillAudioDuration(AudioEventEntity entity, AudioEvent model) {
        entity.setDuration(model.getDuration());
    }

}
