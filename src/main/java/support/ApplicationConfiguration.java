package support;

import java.util.Properties;

import fr.xebia.extras.selma.Selma;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import services.EventMapper;
import services.UUIDMapper;

@Configuration
@ComponentScan({"api", "persistent", "services","support","factory", "converters"})
public class ApplicationConfiguration {
    @Bean public ObjectMapper mapper(){
        return new ObjectMapper();
    }
    @Bean
    public PropertySourcesPlaceholderConfigurer properties() {
        final PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        final Properties properties = new Properties();
        properties.setProperty("store.path", "/tmp");
        propertySourcesPlaceholderConfigurer.setProperties(properties);
        return propertySourcesPlaceholderConfigurer;
    }

    @Bean
    public EventMapper provideEventMapper(UUIDMapper service){
        return Selma.getMapperWithCustom(EventMapper.class, service);
    }
}
