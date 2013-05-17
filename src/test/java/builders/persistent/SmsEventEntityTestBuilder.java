package builders.persistent;

import factory.MsisdnGenerator;
import persistent.SmsEventEntity;

public class SmsEventEntityTestBuilder extends EventEntityTestBuilder<SmsEventEntityTestBuilder, SmsEventEntity> {

    private String from;
    private String text;

    public static SmsEventEntityTestBuilder sms() {
        return new SmsEventEntityTestBuilder();
    }

    public SmsEventEntity build() {
        SmsEventEntity sms = new SmsEventEntity();
        super.build(sms);

        sms.setFrom(from);
        sms.setText(text);

        return sms;
    }

    public SmsEventEntityTestBuilder from(String interlocutorMsisdn) {
        this.from = interlocutorMsisdn;
        return this;
    }

    public SmsEventEntityTestBuilder randomFrom() {
        this.from = MsisdnGenerator.randomizedFr();
        return this;
    }

    public SmsEventEntityTestBuilder text(String text) {
        this.text = text;
        return this;
    }

}
