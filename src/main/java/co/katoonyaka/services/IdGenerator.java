package co.katoonyaka.services;

import co.katoonyaka.domain.PortfolioEntity;

import java.util.List;

public interface IdGenerator {

    String generateId(List<? extends PortfolioEntity> entities);

}
