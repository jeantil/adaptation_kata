package acceptance;

import java.io.File;
import java.io.IOException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mock.env.MockEnvironment;
import support.ApplicationConfiguration;

public class BaseAcceptanceIT {
    @Rule
    public TemporaryFolder folder= new TemporaryFolder();

    protected AnnotationConfigApplicationContext initializeApplication() {
        final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(ApplicationConfiguration.class);
        final MockEnvironment mockEnvironment = new MockEnvironment();
        mockEnvironment.setProperty("store.path", folder.getRoot().getAbsolutePath());
        mockEnvironment.setProperty("url.base", "file://");
        ctx.setEnvironment(mockEnvironment);
        ctx.refresh();
        return ctx;
    }

    protected JsonNode readStoredFile(AnnotationConfigApplicationContext ctx) throws IOException {
        final File[] files = folder.getRoot().listFiles();
        final ObjectMapper mapper = ctx.getBean(ObjectMapper.class);
        return mapper.readTree(files[0]);
    }
}
