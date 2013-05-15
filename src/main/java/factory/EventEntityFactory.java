package factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import api.domain.AudioEvent;
import api.domain.Event;
import api.domain.EventVisitor;
import api.domain.FileEvent;
import api.domain.SmsEvent;
import api.domain.TextEvent;
import api.domain.XmppEvent;
import converters.AudioConverter;
import converters.FileConverter;
import converters.SmsConverter;
import converters.TextConverter;
import persistent.AudioEventEntity;
import persistent.EventEntity;
import persistent.FileEventEntity;
import persistent.SmsEventEntity;
import persistent.TextEventEntity;
import persistent.XmppEventEntity;
import persistent.UserEntity;
import services.URLService;

@Component
public class EventEntityFactory implements EventVisitor<EventEntity, UserEntity> {

    @Autowired
    private URLService urlService;

    @Override
    public FileEventEntity visit(FileEvent model, UserEntity user) {
      return new FileConverter(urlService).toEntity(model,user);
    }

    @Override
    public AudioEventEntity visit(AudioEvent model, UserEntity user) {
        return new AudioConverter(urlService).toEntity(model,user);
    }

    @Override
    public SmsEventEntity visit(SmsEvent model, UserEntity user) {
        return new SmsConverter().toEntity(model, user);
    }


    @Override
    public TextEventEntity visit(TextEvent model, UserEntity user) {
        return new TextConverter().toEntity(model,user);
    }

}
