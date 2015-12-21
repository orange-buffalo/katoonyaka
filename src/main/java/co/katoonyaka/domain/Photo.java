package co.katoonyaka.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Photo extends PortfolioEntity {

    @Getter @Setter
    private int width;

    @Getter @Setter
    private int height;

    @Getter @Setter
    private String externalId;

}
