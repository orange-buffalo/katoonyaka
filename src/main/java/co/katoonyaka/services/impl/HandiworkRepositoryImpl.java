package co.katoonyaka.services.impl;

import co.katoonyaka.domain.Handiwork;
import co.katoonyaka.domain.Photo;
import co.katoonyaka.services.HandiworkRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HandiworkRepositoryImpl extends RepositoryBaseImpl<Handiwork> implements HandiworkRepository {

    @Override
    public Handiwork findByUrl(String url) {
        return findAll().stream()
                .filter(handiwork -> url.equals(handiwork.getUrl()))
                .findAny()
                .orElse(null);
    }

    @Override
    public void save(Handiwork handiwork) {
        List<Photo> photos = handiwork.getAllPhotos();
        photos.stream()
                .filter(photo -> photo.getId() == null)
                .forEach(photo -> photo.setId(idGenerator.generateId(photos)));
        super.save(handiwork);
    }

    @Override
    public List<Handiwork> findAllPublished() {
        return findAll().stream()
                .filter(handiwork -> !handiwork.isDraft())
                .collect(Collectors.toList());
    }

    @Override
    protected List<Handiwork> getEntitiesFromStorage() {
        return storage.getHandiworks();
    }

}
