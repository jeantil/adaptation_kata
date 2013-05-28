package services;

import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class URLService {


    private String urlBase;

    @Autowired
    public URLService(@Value("${url.base=file://}") String urlBase) {
        this.urlBase = urlBase;
    }

    public URL toUrl(String filename) throws MalformedURLException {
        // super duper hyper secure encryption scheme should be here
        // the point is to "need" an external service for some conversions

        return  new URL(urlBase+filename);

    }

    public String fromUrl(URL url) {
        return url.getFile();
    }
}
