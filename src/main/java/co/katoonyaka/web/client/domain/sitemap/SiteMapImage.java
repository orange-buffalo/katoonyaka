package co.katoonyaka.web.client.domain.sitemap;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SiteMapImage {

    @XmlElement(name = "loc", namespace = "http://www.google.com/schemas/sitemap-image/1.1")
    private String location;

    @XmlElement(name = "title", namespace = "http://www.google.com/schemas/sitemap-image/1.1")
    private String title;

    public SiteMapImage(String location, String title) {
        this.location = location;
        this.title = title;
    }

}
