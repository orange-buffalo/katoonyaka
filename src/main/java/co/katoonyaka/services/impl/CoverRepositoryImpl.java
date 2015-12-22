package co.katoonyaka.services.impl;

import co.katoonyaka.domain.Cover;
import co.katoonyaka.services.CoverRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoverRepositoryImpl extends RepositoryBaseImpl<Cover> implements CoverRepository {

    @Override
    public void save(Cover entity) {
        if (entity.getPhoto() != null && entity.getPhoto().getId() == null) {
            entity.getPhoto().setId(idGenerator.generateId(Collections.EMPTY_LIST));
        }
        super.save(entity);
    }

    @Override
    protected List<Cover> getEntitiesFromStorage() {
        return storage.getCovers();
    }

    @Override
    public List<Cover> findAllPublished() {
        return findAll().stream()
                .filter(cover -> !cover.getDraft())
                .collect(Collectors.toList());
    }

}
