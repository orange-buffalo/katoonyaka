package co.katoonyaka.services.impl;

import co.katoonyaka.domain.PhotoSizesConfig;
import co.katoonyaka.services.ConfigService;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import java.util.Arrays;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

@Component
@Slf4j
public class ConfigServiceImpl implements ConfigService {

    private static final String CONFIG_FILE_PATH = System.getenv("KATOONYAKA_HOME") + "/config.js";

    @Getter
    private boolean devMode;

    @Getter
    private Collection<String> adminUsers;

    private Object configData;

    private final PhotoSizesConfig photoSizesConfig = new PhotoSizesConfig(".", Arrays.asList(
            new PhotoSizesConfig.PhotoSize("micro", 200),
            new PhotoSizesConfig.PhotoSize("mini", 300),
            new PhotoSizesConfig.PhotoSize("tiny", 500),
            new PhotoSizesConfig.PhotoSize("small", 650),
            new PhotoSizesConfig.PhotoSize("medium", 1024),
            new PhotoSizesConfig.PhotoSize("big", 1400),
            new PhotoSizesConfig.PhotoSize("large", 2048)
    ));

    @PostConstruct
    private void init() {
        File file = new File(CONFIG_FILE_PATH);
        if (!file.exists()) {
            log.error("config file [{}] does not exist", CONFIG_FILE_PATH);
            throw new IllegalStateException(CONFIG_FILE_PATH + " does not exist");
        }

        log.info("loading data from file [{}]", CONFIG_FILE_PATH);
        try (FileInputStream inputStream = new FileInputStream(file)) {
            String jsonContent = IOUtils.toString(inputStream);
            this.configData = Configuration.defaultConfiguration().jsonProvider().parse(jsonContent);

            this.devMode = JsonPath.read(configData, "$.devMode");

            Collection<String> adminUsersValue = JsonPath.read(configData, "$.adminUsers");
            this.adminUsers = Collections.unmodifiableSet(new HashSet<>(adminUsersValue));

        } catch (Exception e) {
            log.error("cannot read file " + CONFIG_FILE_PATH, e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public <T> T getConfigValue(String configProperty) {
        return JsonPath.read(configData, "$." + configProperty);
    }

    public PhotoSizesConfig getPhotoSizesConfig() {
        return photoSizesConfig;
    }

}
