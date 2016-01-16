package co.katoonyaka.web.client.domain;

import co.katoonyaka.domain.Handiwork;
import lombok.Getter;

import java.util.List;

public class HomeResponseModel extends ClientResponseModel {

    @Getter
    private List<Handiwork> handiworks;

    public HomeResponseModel(String title, List<Handiwork> handiworks) {
        //todo meta description
        super(title, null);
        this.handiworks = handiworks;
    }

}
