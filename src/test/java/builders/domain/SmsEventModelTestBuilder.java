package builders.domain;

import api.domain.SmsEvent;

public class SmsEventModelTestBuilder extends EventModelTestBuilder<SmsEventModelTestBuilder, SmsEvent> {

    private String from;
    private String text;

    public SmsEvent build() {
        SmsEvent sms = new SmsEvent();
        super.build(sms);

        sms.setFrom(from);
        sms.setText(text);

        return sms;
    }

    public static SmsEventModelTestBuilder sms() {
        return new SmsEventModelTestBuilder();
    }

    public SmsEventModelTestBuilder from(String interlocutorMsisdn) {
        this.from = interlocutorMsisdn;
        return this;
    }

    public SmsEventModelTestBuilder text(String text) {
        this.text = text;
        return this;
    }

}
