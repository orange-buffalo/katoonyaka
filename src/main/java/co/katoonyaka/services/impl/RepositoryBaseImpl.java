package co.katoonyaka.services.impl;

import co.katoonyaka.domain.PortfolioEntity;
import co.katoonyaka.services.IdGenerator;
import co.katoonyaka.services.RepositoryBase;
import co.katoonyaka.services.impl.storage.JsonDataStorage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class RepositoryBaseImpl<E extends PortfolioEntity> implements RepositoryBase<E> {

    @Autowired
    protected JsonDataStorage storage;

    @Autowired
    protected IdGenerator idGenerator;

    @Override
    public void replaceAll(List<E> entities) {
        List<E> storageData = getEntitiesFromStorage();
        storageData.clear();
        storageData.addAll(entities);
        storage.save();
    }

    @Override
    public void move(E entity, int toIndex) {
        List<E> storageData = getEntitiesFromStorage();
        int currentIndex = storageData.indexOf(entity);
        if (toIndex == currentIndex) {
            return;
        } else if (toIndex < currentIndex) {
            storageData.remove(currentIndex);
            storageData.add(toIndex, entity);
        } else {
            storageData.add(toIndex, entity);
            storageData.remove(currentIndex);
        }
        storage.save();
    }

    @Override
    public void save(E entity) {
        List<E> entities = getEntitiesFromStorage();
        if (entity.getId() == null) {
            entity.setId(idGenerator.generateId(entities));
            entities.add(0, entity);
        }
        else {
            int index = entities.indexOf(entity);
            entities.set(index, entity);
        }
        storage.save();
    }

    @Override
    public void delete(E entity) {
        getEntitiesFromStorage().remove(entity);
        storage.save();
    }

    @Override
    public E findById(String id) {
        for (E entity : findAll()) {
            if (id.equals(entity.getId())) {
                return entity;
            }
        }
        return null;
    }

    @Override
    public List<E> findAll() {
        return getEntitiesFromStorage();
    }

    protected abstract List<E> getEntitiesFromStorage();

}
