package co.katoonyaka.services;

import co.katoonyaka.domain.Cover;

import java.util.List;

public interface CoverRepository extends RepositoryBase<Cover> {

    List<Cover> findAllPublished();

}
