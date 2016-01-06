package co.katoonyaka.web.client.domain;

import lombok.Getter;

public class ClientResponseModel {

    @Getter
    private String title;

    public ClientResponseModel(String title) {
        this.title = title;
    }

}
