package factory;

import java.net.MalformedURLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import api.domain.AudioEvent;
import api.domain.Event;
import api.domain.FileEvent;
import api.domain.SmsEvent;
import api.domain.TextEvent;
import api.domain.XmppEvent;
import persistent.AudioEventEntity;
import persistent.EventEntity;
import persistent.FileEventEntity;
import persistent.SmsEventEntity;
import persistent.TextEventEntity;
import persistent.XmppEventEntity;
import services.URLService;

@Component
public class EventModelFactory {

    @Autowired
    private URLService urlService;

    private void mapBaseProperties(Event model, EventEntity entity) {
        model.setId(entity.getId());
    }

    private void mapXmppProperties(XmppEvent model, EventEntity eventEntity) {
        this.mapBaseProperties(model, eventEntity);
        XmppEventEntity entity = (XmppEventEntity) eventEntity;
        model.setXmppId(entity.getXmppId());
    }

    private void mapFileProperties(FileEvent model, FileEventEntity entity) {

        this.mapXmppProperties(model, entity);

        try {
            model.setUrl(urlService.toUrl(entity.getFilename()));
        } catch (MalformedURLException e) { }
    }

    private void mapAudioProperties(AudioEvent model, AudioEventEntity entity) {
        this.mapFileProperties(model, entity);
        model.setDuration(entity.getDuration());

    }

    public FileEvent visit(FileEventEntity entity) {
        FileEvent model = new FileEvent();

        this.mapFileProperties(model, entity);

        return model;
    }

    public AudioEvent visit(AudioEventEntity entity) {
        AudioEvent model = new AudioEvent();

        this.mapAudioProperties(model, entity);

        return model;
    }

    public SmsEvent visit(SmsEventEntity entity) {
        SmsEvent model = new SmsEvent();

        this.mapBaseProperties(model, entity);

        model.setFrom(entity.getFrom());
        model.setText(entity.getText());

        return model;

    }

    public TextEvent visit(TextEventEntity entity) {
        TextEvent model = new TextEvent();

        this.mapXmppProperties(model, entity);

        model.setText(entity.getText());
        model.setXmppId(entity.getXmppId());

        return model;

    }

}
