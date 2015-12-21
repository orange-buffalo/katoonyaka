package co.katoonyaka.web.client.domain;

import co.katoonyaka.domain.Handiwork;
import lombok.experimental.Delegate;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class EnrichedHandiwork {

    private static final List<PhotoSource> PHOTO_SOURCES = PhotoSourcesBuilder.start(1800)
            .add(1300)
            .add(1000)
            .add(700)
            .add(300)
            .add(200)
            .add(140)
            .add(0)
            .build();

    @Delegate
    private Handiwork handiwork;

    public EnrichedHandiwork(Handiwork handiwork) {
        this.handiwork = handiwork;
    }

    public List<HandiworkDescriptionSection> getSections() {
        List<HandiworkDescriptionSection> sections = new ArrayList<>();
        String currentHeader = null;
        String currentContent = null;
        for (String line : handiwork.getDescription().split("\\r?\\n")) {
            if (line.contains("{") && line.contains("}")) {
                if (currentContent != null) {
                    sections.add(new HandiworkDescriptionSection(currentHeader, currentContent));
                }
                currentHeader = line.replace("{ ", "").replace("{", "")
                        .replace("}", "").replace(" }", "");
                currentContent = "";
            } else {
                if (StringUtils.isNotEmpty(line)) {
                    currentContent += "<p>" + line + "</p>";
                }
            }
        }
        sections.add(new HandiworkDescriptionSection(currentHeader, currentContent));
        return sections;
    }

    public List<PhotoSource> getPhotoSources() {
        return PHOTO_SOURCES;
    }

}
