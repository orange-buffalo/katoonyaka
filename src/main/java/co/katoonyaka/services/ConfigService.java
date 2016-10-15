package co.katoonyaka.services;

import co.katoonyaka.domain.PhotoSizesConfig;
import java.util.Collection;

public interface ConfigService {

    boolean isDevMode();

    Collection<String> getAdminUsers();

    <T> T getConfigValue(String configProperty);

    PhotoSizesConfig getPhotoSizesConfig();

}
