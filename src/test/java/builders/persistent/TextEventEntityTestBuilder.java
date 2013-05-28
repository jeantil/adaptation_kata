package builders.persistent;

import persistent.TextEventEntity;

public class TextEventEntityTestBuilder extends XmppEventEntityTestBuilder<TextEventEntityTestBuilder,
        TextEventEntity> {

    private String text;

    public static TextEventEntityTestBuilder text() {
        return new TextEventEntityTestBuilder();
    }

    public TextEventEntity build() {
        TextEventEntity textEntity = new TextEventEntity();
        super.build(textEntity);

        textEntity.setText(text);

        return textEntity;
    }

    public TextEventEntityTestBuilder text(String text) {
        this.text = text;
        return this;
    }

}
