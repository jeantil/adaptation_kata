package converters;

import api.event.domain.TextEvent;
import persistent.TextEventEntity;
import persistent.UserEntity;

public class TextConverter extends XmppEventConverter<TextEventEntity,TextEvent> {

    public TextEventEntity toEntity(TextEvent model, UserEntity user) {
        final TextEventEntity text = super.toEntity(new TextEventEntity(), model, user);
        text.setText(model.getText());
        return text;
    }
}
