package persistent;

import api.domain.AudioEvent;
import api.domain.FileEvent;
import api.domain.SmsEvent;

public interface EventEntityVisitor {
    FileEvent visit(FileEventEntity fileEventEntity);

    AudioEvent visit(AudioEventEntity audioEventEntity);

    SmsEvent visit(SmsEventEntity eventEntity);
}
