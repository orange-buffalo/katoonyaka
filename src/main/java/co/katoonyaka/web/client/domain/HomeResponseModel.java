package co.katoonyaka.web.client.domain;

import co.katoonyaka.domain.Handiwork;
import lombok.Getter;

import java.util.List;

public class HomeResponseModel extends ClientResponseModel {

    @Getter
    private List<Handiwork> handiworks;

    public HomeResponseModel(String title,
                             String metaDescription,
                             String relativeImageUrl,
                             String relativeUrl,
                             List<Handiwork> handiworks) {
        super(title, metaDescription, relativeImageUrl, relativeUrl);
        this.handiworks = handiworks;
    }

}
