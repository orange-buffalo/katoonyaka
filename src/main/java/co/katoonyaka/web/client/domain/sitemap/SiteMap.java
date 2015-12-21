package co.katoonyaka.web.client.domain.sitemap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;

@XmlRootElement(name="urlset", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
public class SiteMap {

    @XmlElement(name = "url", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
    private Collection<SiteMapUrl> urls = new ArrayList<>();

    public void addUrl(SiteMapUrl url) {
        urls.add(url);
    }

}
