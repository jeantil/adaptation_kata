package api.domain;

public interface EventVisitor<E, U> {

    public E visit(FileEvent model, U user);

    public E visit(AudioEvent model, U user);

    public E visit(TextEvent model, U user);

    public E visit(SmsEvent model, U user);
}
