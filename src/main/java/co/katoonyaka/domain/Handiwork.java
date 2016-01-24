package co.katoonyaka.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Handiwork extends PortfolioEntity {

    @Getter @Setter
    private List<Photo> photos = new ArrayList<>();

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String etsyUrl;

    @Getter @Setter
    private String dawandaUrl;

    @Getter @Setter
    private Photo cover;

    @Getter @Setter
    private String description;

    @Getter @Setter
    private String summary;

    @Setter @Getter
    private String url;

    @Getter @Setter
    private boolean draft = false;

    public Photo getPhoto(String photoId) {
        return getAllPhotos().stream()
                .filter(photo -> photoId.equals(photo.getId()))
                .findFirst()
                .orElse(null);
    }

    public List<Photo> getAllPhotos() {
        List<Photo> allPhotos = new ArrayList<>(photos);
        if (cover != null) {
            allPhotos.add(cover);
        }
        return allPhotos;
    }

}
