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
        while (true) {
            final String id = RandomStringUtils.randomAlphabetic(5);

            boolean idUsed = entities.stream()
                    .filter(entity -> id.equals(entity.getId()))
                    .findAny()
                    .isPresent();

            if (!idUsed) {
                return id;
            }
        }
    }
}
