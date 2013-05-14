package converters;

import api.event.domain.TextEvent;
import persistent.TextEventEntity;
import persistent.UserEntity;

public class TextConverter {
    public TextEventEntity toEntity(TextEvent model, UserEntity user) {
        final TextEventEntity text = new TextEventEntity();
        text.setId(model.getId());
        text.setUserId(user.getUserId());
        text.setXmppId(model.getXmppId());
        text.setText(model.getText());
        return text;
    }
}
