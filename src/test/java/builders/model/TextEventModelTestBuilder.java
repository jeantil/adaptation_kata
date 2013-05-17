package builders.model;


import api.event.domain.TextEvent;

public class TextEventModelTestBuilder extends XmppEventModelTestBuilder<TextEventModelTestBuilder, TextEvent> {

    private String text;

    public static TextEventModelTestBuilder text() {
        return new TextEventModelTestBuilder();
    }

    public TextEvent build() {
        TextEvent textModel = new TextEvent();
        super.build(textModel);
        textModel.setText(this.text);
        return textModel;
    }

    public TextEventModelTestBuilder text(String text) {
        this.text = text;
        return this;
    }
}
