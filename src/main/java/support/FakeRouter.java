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
class FakeRouter implements Router {
    private static final Logger logger = LoggerFactory.getLogger("router");
    private final EventResource resource;
    private final ObjectMapper mapper;

    @Autowired
    public FakeRouter(EventResource resource, ObjectMapper mapper) {
        this.resource = resource;
        this.mapper = mapper;
    }

    @Override
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
        }catch (RouteError e){
            throw e;
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


}

class Error {
    static RouteError methodNotAllowed() {
        return new RouteError("405, Method not allowed");
    }

    static RouteError resourceNotFound() {
        return new RouteError("404, Resource not found");
    }

    static RouteError internalServerError() {
        return new RouteError("500, Internal server error");
    }
}