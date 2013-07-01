package support;

import java.io.IOException;
import java.util.UUID;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import api.EventResource;
import api.domain.Event;

@Component
public class TrivialRouter {
    private static Logger logger = LoggerFactory.getLogger("router");
    private EventResource resource;
    private ObjectMapper mapper;

    @Autowired
    public TrivialRouter(EventResource resource, ObjectMapper mapper) {
        this.resource = resource;
        this.mapper = mapper;
    }

    public String route(Verb verb, String path, String body) {
        try {
            switch (verb) {
                case GET:
                    return get(path, body);
                case POST:
                    return post(path, body);
                default:
                    throw Error.methodNotAllowed();
            }
        } catch (Exception e){
            logger.info("Internal Server Error : ",e);
            throw Error.internalServerError();
        }
    }

    private String get(String path, String body) throws IOException {
        switch (path) {
            case "/events":
                return mapper.writeValueAsString(resource.get(UUID.fromString(body)));
            case "/events/xmpp":
                return mapper.writeValueAsString(resource.get(body));
            default:
                throw Error.resourceNotFound();
        }
    }

    private String post(String path, String body) throws IOException {
        switch (path) {
            case "/events":
                return mapper.writeValueAsString(resource.post(mapper.readValue(body, Event.class)));
            default:
                throw Error.resourceNotFound();
        }
    }


    public enum Verb {
        POST, GET
    }
}

class Error {
    static IllegalArgumentException methodNotAllowed() {
        return new IllegalArgumentException("405, Method not allowed");
    }

    static IllegalArgumentException resourceNotFound() {
        return new IllegalArgumentException("404, Resource not found");
    }

    static IllegalArgumentException internalServerError() {
        return new IllegalArgumentException("500, Internal server error");
    }
    static IllegalArgumentException badRequest() {
        return new IllegalArgumentException("400, Bad Request");
    }
}