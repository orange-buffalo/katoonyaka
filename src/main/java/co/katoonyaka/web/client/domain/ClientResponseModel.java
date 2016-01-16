package co.katoonyaka.web.client.domain;

import lombok.Getter;

public class ClientResponseModel {

    @Getter
    private String title;

    @Getter
    private String metaDescription;

    @Getter
    private String relativeImageUrl;

    @Getter
    private String relativeUrl;

    public ClientResponseModel(String title,
                               String metaDescription,
                               String relativeImageUrl,
                               String relativeUrl) {
        this.title = title;
        this.metaDescription = metaDescription;
        this.relativeImageUrl = relativeImageUrl;
        this.relativeUrl = relativeUrl;
    }

}
