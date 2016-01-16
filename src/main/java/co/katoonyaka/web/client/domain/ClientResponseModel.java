package co.katoonyaka.web.client.domain;

import lombok.Getter;

public class ClientResponseModel {

    @Getter
    private String title;

    @Getter
    private String metaDescription;

    public ClientResponseModel(String title, String metaDescription) {
        this.title = title;
        this.metaDescription = metaDescription;
    }

}
