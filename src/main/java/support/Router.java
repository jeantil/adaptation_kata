package support;

public interface Router {
    String route(Verb verb, String path, String body);

    public enum Verb {
        POST, GET, PUT
    }
}
