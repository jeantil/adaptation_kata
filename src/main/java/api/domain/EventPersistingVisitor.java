package api.domain;

public interface EventPersistingVisitor {
    public void visit(SmsEvent smsEvent);

    public void visit(FileEvent fileEvent);

    public void visit(AudioEvent audioEvent);
}
