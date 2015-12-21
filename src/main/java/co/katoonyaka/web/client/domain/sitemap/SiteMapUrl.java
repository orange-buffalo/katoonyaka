package co.katoonyaka.web.client.domain.sitemap;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.Collection;

public class SiteMapUrl {

    @XmlElement(name = "loc", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
    private String location;

    @XmlElement(name = "image", namespace = "http://www.google.com/schemas/sitemap-image/1.1")
    private Collection<SiteMapImage> images = new ArrayList<>();

    public SiteMapUrl(String location) {
        this.location = location;
    }

    public void addImage(SiteMapImage image) {
        images.add(image);
    }

}
