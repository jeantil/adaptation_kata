package converters;

import persistent.SmsEventEntity;
import persistent.UserEntity;

public class SmsConverter extends EventConverter{
    public SmsEventEntity toEntity(api.event.domain.SmsEvent model, UserEntity user) {
        final SmsEventEntity sms = super.toEntity(new SmsEventEntity(), model, user);
        sms.setFrom(model.getFrom());
        sms.setText(model.getText());
        return sms;
    }
}
