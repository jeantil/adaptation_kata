package converters;

import api.event.domain.TextEvent;
import persistent.TextEventEntity;
import persistent.UserEntity;

public class TextConverter extends EventConverter {
    public TextEventEntity toEntity(TextEvent model, UserEntity user) {
        final TextEventEntity text = super.toEntity(new TextEventEntity(), model, user);
        text.setXmppId(model.getXmppId());
        text.setText(model.getText());
        return text;
    }
}
