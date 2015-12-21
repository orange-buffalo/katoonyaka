package co.katoonyaka.services.impl.storage;

import co.katoonyaka.domain.Cover;
import co.katoonyaka.domain.Handiwork;
import co.katoonyaka.services.IdGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Slf4j
@Component
@Lazy(false)
public class JsonDataStorage {

    private static final String DATA_FILE_PATH = System.getenv("katoonyaka.home") + "/storage.dat";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IdGenerator idGenerator;

    @Getter
    private List<Handiwork> handiworks;

    @Getter
    private List<Cover> covers;

    @PostConstruct
    private void loadData() {
        File dataFile = new File(DATA_FILE_PATH);
        if (dataFile.exists()) {
            log.info("loading data from file [{}]", DATA_FILE_PATH);
            try (FileInputStream fileInputStream = new FileInputStream(dataFile))
            {
                InputStream inputStream = new GZIPInputStream(fileInputStream);
                JsonKatoonyakaData data = objectMapper.readValue(inputStream, JsonKatoonyakaData.class);
                handiworks = data.getHandiworks();
                covers = data.getCovers();

            } catch (Exception e) {
                log.error("cannot read file " + DATA_FILE_PATH, e);
            }
        } else {
            log.warn("file [{}] does not exist", DATA_FILE_PATH);
        }
    }

    public void save() {
        File dataFile = new File(DATA_FILE_PATH);
        try (FileOutputStream fileOutputStream = new FileOutputStream(dataFile);
            GZIPOutputStream outputStream = new GZIPOutputStream(fileOutputStream)) {

            JsonKatoonyakaData data = new JsonKatoonyakaData();
            data.setHandiworks(handiworks);
            data.setCovers(covers);
            objectMapper.writeValue(outputStream, data);

        } catch (Exception e) {
            log.error("cannot save file " + DATA_FILE_PATH, e);
        }
    }
}
