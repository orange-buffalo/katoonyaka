package co.katoonyaka.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Cover extends PortfolioEntity {

    private Boolean draft;
    private Photo photo;
    private String text;
    private CoverHorizontalAlignment horizontalAlignment;
    private CoverVerticalAlignment verticalAlignment;

    public Cover() {
        this.draft = true;
    }

}
