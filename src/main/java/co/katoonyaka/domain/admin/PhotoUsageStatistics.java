package co.katoonyaka.domain.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Collection;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class PhotoUsageStatistics {
    private Collection<String> unusedExternalPhotos;
    private int totalExternalPhotosCount;
    private List<PhotoUsageItem> photosUsage;
}
