package co.katoonyaka.services.impl;

import co.katoonyaka.ApplicationException;
import co.katoonyaka.domain.Photo;
import co.katoonyaka.domain.uc.UcFile;
import co.katoonyaka.domain.uc.UcPage;
import co.katoonyaka.services.ConfigService;
import co.katoonyaka.services.PhotoStorage;
import com.google.common.collect.Collections2;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class UploadCarePhotoStorage implements PhotoStorage {

    private static final int MAX_UPLOADCARE_SIZE = 2048;

    @Autowired
    private ConfigService configService;

    private String publicKey;
    private String privateKey;

    @PostConstruct
    private void init() {
        publicKey = configService.getConfigValue("uploadcare.publicKey");
        privateKey = configService.getConfigValue("uploadcare.privateKey");
    }

    @Override
    public void loadPhoto(Photo photo, OutputStream stream, Integer width, Integer height) {
        if (width == null) {
            throw new ApplicationException("Width is not specified");
        }

        if (height == null) {
            if (width > MAX_UPLOADCARE_SIZE) {
                width = MAX_UPLOADCARE_SIZE;
            }
            double ratio = photo.getWidth() / (double) photo.getHeight();
            height = (int) Math.floor(width / ratio);
            if (height > MAX_UPLOADCARE_SIZE) {
                height = MAX_UPLOADCARE_SIZE;
                width = (int) Math.floor(height * ratio);
            }
        }

        loadPhoto(photo, "progressive/yes/-/scale_crop/" + width + "x" + height + "/center", stream);
    }

    private void loadPhoto(Photo photo, String controlPart, OutputStream outputStream) {
        try (InputStream inputStream = getUploadcareStream(photo, controlPart)) {
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    private InputStream getUploadcareStream(Photo photo, String controlPart) throws IOException {
        return new URL("http://www.ucarecdn.com/" + photo.getExternalId() + "/-/" + controlPart + "/")
                .openStream();
    }

    @Override
    public void removePhoto(String photo) {
        try {
            HttpURLConnection connection = getUploadcareConnection("https://api.uploadcare.com/files/" + photo + "/");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestMethod("DELETE");
            String theString = readStringData(connection);
            log.info("after file remove: " + theString);
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public Collection<String> getRegisteredExternalIds() {
        List<UcFile> files = new ArrayList<>();
        loadNextPageOfFiles("https://api.uploadcare.com/files/", files);
        return Collections2.transform(files, UcFile::getUuid);
    }

    private void loadNextPageOfFiles(String pageUrl, List<UcFile> files) {
        try {
            HttpURLConnection connection = getUploadcareConnection(pageUrl);
            String jsonString = readStringData(connection);

            Gson gson = new Gson();
            Type type = new TypeToken<UcPage<UcFile>>() {
            }.getType();
            UcPage<UcFile> page = gson.fromJson(jsonString, type);
            files.addAll(page.getResults());
            if (page.getNext() != null) {
                loadNextPageOfFiles(page.getNext(), files);
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    private HttpURLConnection getUploadcareConnection(String pageUrl) throws IOException {
        URL url = new URL(pageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Accept", "application/vnd.uploadcare-v0.3+json");
        connection.setRequestProperty("Date", (new Date()).toString());
        connection.setRequestProperty("Authorization", "Uploadcare.Simple " + publicKey + ":" + privateKey);
        return connection;
    }

    private String readStringData(HttpURLConnection connection) throws IOException {
        try {
            connection.connect();
            return IOUtils.toString(connection.getInputStream());
        } finally {
            connection.disconnect();
        }
    }

}
