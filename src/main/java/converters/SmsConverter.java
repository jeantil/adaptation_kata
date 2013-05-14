package converters;

import api.event.domain.SmsEvent;
import persistent.SmsEventEntity;
import persistent.UserEntity;

public class SmsConverter extends EventConverter<SmsEventEntity,SmsEvent> {

    public SmsEventEntity toEntity(api.event.domain.SmsEvent model, UserEntity user) {
        final SmsEventEntity sms = super.toEntity(new SmsEventEntity(), model, user);
        sms.setFrom(model.getFrom());
        sms.setText(model.getText());
        return sms;
    }

}
