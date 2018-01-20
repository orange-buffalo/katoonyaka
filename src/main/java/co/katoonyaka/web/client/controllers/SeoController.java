package co.katoonyaka.web.client.controllers;

import co.katoonyaka.domain.Handiwork;
import co.katoonyaka.domain.Photo;
import co.katoonyaka.domain.PhotoSizesConfig;
import co.katoonyaka.services.ConfigService;
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

    private final HandiworkRepository handiworkRepository;

    private final ConfigService configService;

    @Autowired
    public SeoController(HandiworkRepository handiworkRepository, ConfigService configService) {
        this.handiworkRepository = handiworkRepository;
        this.configService = configService;
    }

    @RequestMapping(value = {"/sitemap", "/sitemap.xml"}, produces = {MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public SiteMap siteMap() {
        String baseUrl = "https://katoonyaka.co/";
        SiteMap siteMap = new SiteMap();
        SiteMapUrl homeUrl = new SiteMapUrl(baseUrl);

        PhotoSizesConfig photoSizesConfig = configService.getPhotoSizesConfig();
        String photoUrlSuffix = photoSizesConfig.getNameSeparator() + photoSizesConfig.findSizeName(1200) + ".jpeg";

        for (Handiwork handiwork : handiworkRepository.findAllPublished()) {
            SiteMapUrl url = new SiteMapUrl(baseUrl + "portfolio/" + handiwork.getUrl());
            for (Photo photo : handiwork.getPhotos()) {
                SiteMapImage image = new SiteMapImage(
                        baseUrl + "photos/" + handiwork.getUrl() + "." + photo.getId() + photoUrlSuffix,
                        handiwork.getName());
                url.addImage(image);
            }
            siteMap.addUrl(url);

            homeUrl.addImage(new SiteMapImage(
                    baseUrl + "photos/" + handiwork.getUrl() + "." + handiwork.getCover().getId() + ".jpeg",
                    handiwork.getName()));
        }
        siteMap.addUrl(homeUrl);

        return siteMap;
    }
}
