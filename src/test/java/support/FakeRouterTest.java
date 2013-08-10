package support;

import static org.mockito.Mockito.mock;
import static support.Router.Verb.GET;
import static support.Router.Verb.PUT;
import static support.matchers.ContainsString.containsString;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import api.EventResource;
import services.EventVisitingResource;

public class FakeRouterTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void should_throw_405_for_invalid_method() throws Exception {
        // Given
        ObjectMapper mapper = mock(ObjectMapper.class);
        EventResource resource = mock(EventVisitingResource.class);
        FakeRouter router = new FakeRouter(resource, mapper);
        exception.expect(RouteError.class);
        exception.expectMessage(containsString("405"));
                // When
                router.route(PUT, "/events", "");
    }

    @Test
    public void should_throw_404_for_resource_not_found() throws Exception {
        // Given
        ObjectMapper mapper = mock(ObjectMapper.class);
        EventResource resource = mock(EventVisitingResource.class);
        FakeRouter router = new FakeRouter(resource, mapper);
        exception.expect(RouteError.class);
        exception.expectMessage(containsString(""));
        // When
        router.route(GET, "/toto", "");
    }

    @Test
    public void should_throw_500_for_internal_error() throws Exception {
        // Given
        ObjectMapper mapper = mock(ObjectMapper.class);
        EventResource resource = mock(EventVisitingResource.class);
        FakeRouter router = new FakeRouter(resource, mapper);
        exception.expect(RouteError.class);
        exception.expectMessage(containsString("500"));
        // When
        router.route(GET, "/events", "");
    }
}


