package co.katoonyaka.services.impl;

import co.katoonyaka.domain.Handiwork;
import co.katoonyaka.domain.Photo;
import co.katoonyaka.services.HandiworkRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HandiworkRepositoryImpl extends RepositoryBaseImpl<Handiwork> implements HandiworkRepository {

    @Override
    public Handiwork findByUrl(String url) {
        for (Handiwork handiwork : findAll()) {
            if (url.equals(handiwork.getUrl())) {
                return handiwork;
            }
        }
        return null;
    }

    @Override
    public void save(Handiwork handiwork) {
        List<Photo> photos = handiwork.getPhotos();
        for (Photo photo : photos) {
            if (photo.getId() == null) {
                photo.setId(idGenerator.generateId(photos));
            }
        }
        super.save(handiwork);
    }

    @Override
    public List<Handiwork> findAllPublished() {
        List<Handiwork> allHandiworks = findAll();
        List<Handiwork> handiworks = new ArrayList<>(allHandiworks.size());
        for (Handiwork handiwork : allHandiworks) {
            if (!handiwork.isDraft()) {
                handiworks.add(handiwork);
            }
        }
        return handiworks;
    }

    @Override
    protected List<Handiwork> getEntitiesFromStorage() {
        return storage.getHandiworks();
    }

}
