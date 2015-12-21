package co.katoonyaka.services;

import co.katoonyaka.domain.PortfolioEntity;

import java.util.List;

public interface RepositoryBase<E extends PortfolioEntity> {

    void replaceAll(List<E> entities);

    void move(E entity, int toIndex);

    void save(E entity);

    void delete(E entity);

    E findById(String id);

    List<E> findAll();

    List<E> findAllPublished();

}
