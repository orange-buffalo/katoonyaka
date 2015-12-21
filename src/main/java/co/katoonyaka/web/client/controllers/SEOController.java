package co.katoonyaka.web.client.controllers;

import co.katoonyaka.domain.Handiwork;
import co.katoonyaka.domain.Photo;
import co.katoonyaka.services.HandiworkRepository;
import co.katoonyaka.web.client.domain.sitemap.SiteMap;
import co.katoonyaka.web.client.domain.sitemap.SiteMapImage;
import co.katoonyaka.web.client.domain.sitemap.SiteMapUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SeoController {

    @Autowired
    private HandiworkRepository handiworkRepository;

    @RequestMapping(value = {"/sitemap", "/sitemap.xml"}, produces = {MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public SiteMap siteMap() {
        String baseUrl = "http://katoonyaka.co/";
        SiteMap siteMap = new SiteMap();
        siteMap.addUrl(new SiteMapUrl(baseUrl));
        for (Handiwork handiwork : handiworkRepository.findAllPublished()) {
            SiteMapUrl url = new SiteMapUrl(baseUrl + "portfolio/" + handiwork.getUrl());
            for (Photo photo : handiwork.getPhotos()) {
                SiteMapImage image = new SiteMapImage(baseUrl + "photos/" + handiwork.getUrl() + "." +
                        photo.getId() + ".jpeg");
                url.addImage(image);
            }
            siteMap.addUrl(url);
        }

        return siteMap;
    }
}
