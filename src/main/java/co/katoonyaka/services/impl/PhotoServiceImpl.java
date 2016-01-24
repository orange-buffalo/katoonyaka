package co.katoonyaka.services.impl;

import co.katoonyaka.domain.Handiwork;
import co.katoonyaka.domain.Photo;
import co.katoonyaka.domain.admin.PhotoUsageItem;
import co.katoonyaka.domain.admin.PhotoUsageStatistics;
import co.katoonyaka.services.CoverRepository;
import co.katoonyaka.services.HandiworkRepository;
import co.katoonyaka.services.PhotoService;
import co.katoonyaka.services.PhotoStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoStorage photoStorage;

    @Autowired
    private HandiworkRepository handiworkRepository;

    @Autowired
    private CoverRepository coverRepository;

    @Override
    public PhotoUsageStatistics getPhotoUsageStatistics() {
        Collection<String> registeredExternalIds = photoStorage.getRegisteredExternalIds();
        Collection<String> unusedExernalIds = new ArrayList<>(registeredExternalIds);
        List<? extends Handiwork> handiworks = handiworkRepository.findAll();
        List<PhotoUsageItem> photoUsageItems = new ArrayList<>();
        for (Handiwork handiwork : handiworks) {
            int count = 0;
            for (Photo photo : handiwork.getAllPhotos()) {
                if (registeredExternalIds.contains(photo.getExternalId())) {
                    count++;
                    unusedExernalIds.remove(photo.getExternalId());
                }
            }
            photoUsageItems.add(new PhotoUsageItem("Handiwork", handiwork.getName(), count));
        }

        coverRepository.findAll().stream()
                .filter(cover -> cover.getPhoto() != null && cover.getPhoto().getExternalId() != null)
                .filter(cover -> registeredExternalIds.contains(cover.getPhoto().getExternalId()))
                .forEach(cover -> {
                    unusedExernalIds.remove(cover.getPhoto().getExternalId());

                    photoUsageItems.add(new PhotoUsageItem("Cover", cover.getId(), 1));
                });

        return PhotoUsageStatistics.builder()
                .photosUsage(photoUsageItems)
                .totalExternalPhotosCount(registeredExternalIds.size())
                .unusedExternalPhotos(unusedExernalIds)
                .build();
    }

    @Override
    public void removeExternalResourcesByIds(Collection<String> externalIds) {
        for (String externalId : externalIds) {
            photoStorage.removePhoto(externalId);
        }
    }

}
