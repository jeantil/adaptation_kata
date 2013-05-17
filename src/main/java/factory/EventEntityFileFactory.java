package factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import persistent.FileEventEntity;
import services.CryptoService;

@Component
public class EventEntityFileFactory {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${image.preview.thumbnail.width:200}")
    private int thumbnailWidth;

    @Autowired
    private CryptoService cryptoService;

    private String filename;
    private Long userId;

    public FileEventEntity build() {

        FileEventEntity built = new FileEventEntity();

        built.setUserId(userId);
        built.setFilename(cryptoService.cryptFilename(filename));

        return built;
    }

    public EventEntityFileFactory userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public EventEntityFileFactory filename(String filename) {
        this.filename = filename;
        return this;
    }

}
