package co.katoonyaka.web.client.domain;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class HandiworkDescriptionSection {
    private String header;
    private String content;

    public HandiworkDescriptionSection(String header, String content) {
        this.header = StringUtils.trim(header);
        this.content = content;
    }

}
