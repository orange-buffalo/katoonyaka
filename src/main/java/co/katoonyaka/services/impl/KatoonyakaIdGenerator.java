package co.katoonyaka.services.impl;

import co.katoonyaka.domain.PortfolioEntity;
import co.katoonyaka.services.IdGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KatoonyakaIdGenerator implements IdGenerator {

    @Override
    public String generateId(List<? extends PortfolioEntity> entities) {
        boolean exists = true;
        String newId = null;
        while (exists) {
            newId = RandomStringUtils.randomAlphabetic(5);

            exists = false;
            for (PortfolioEntity entity : entities) {
                if (newId.equals(entity.getId())) {
                    exists = true;
                    break;
                }
            }
        }
        return newId;
    }
}
