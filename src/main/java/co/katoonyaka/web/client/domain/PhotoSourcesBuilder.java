package co.katoonyaka.web.client.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PhotoSourcesBuilder {

    private List<PhotoSource> photoSources = new ArrayList<>();
    private int previousWidth;

    public static PhotoSourcesBuilder start(int maxWidth) {
        PhotoSourcesBuilder builder = new PhotoSourcesBuilder();
        builder.previousWidth = maxWidth;
        return builder;
    }

    public PhotoSourcesBuilder add(int width) {
        if (width > previousWidth) {
            throw new IllegalArgumentException();
        }

        photoSources.add(new PhotoSource(previousWidth, width));
        previousWidth = width;

        return this;
    }

    public List<PhotoSource> build() {
        return photoSources;
    }
}
