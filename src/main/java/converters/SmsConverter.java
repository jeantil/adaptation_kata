package converters;

import persistent.SmsEventEntity;
import persistent.UserEntity;

public class SmsConverter {
    public SmsEventEntity toEntity(api.event.domain.SmsEvent model, UserEntity user) {
        final SmsEventEntity sms = new SmsEventEntity();
        sms.setId(model.getId());
        sms.setUserId(user.getUserId());
        sms.setFrom(model.getFrom());
        sms.setText(model.getText());

        return sms;
    }
}
