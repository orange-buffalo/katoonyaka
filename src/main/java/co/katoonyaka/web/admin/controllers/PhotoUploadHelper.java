package co.katoonyaka.web.admin.controllers;

import co.katoonyaka.domain.Photo;
import co.katoonyaka.services.IdGenerator;
import co.katoonyaka.services.PhotoStorage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Function;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PhotoUploadHelper {

    private final PhotoStorage photoStorage;

    private final IdGenerator idGenerator;

    @Autowired
    public PhotoUploadHelper(PhotoStorage photoStorage, IdGenerator idGenerator) {
        this.photoStorage = photoStorage;
        this.idGenerator = idGenerator;
    }

    public Photo uploadPhoto(MultipartFile photoFile,
                             List<Photo> photos,
                             Function<String, String> fileNamePrefixGenerator) throws IOException {
        String photoId = idGenerator.generateId(photos);
        String fileNamePrefix = fileNamePrefixGenerator.apply(photoId);

        try (InputStream photoFileStream = photoFile.getInputStream()) {
            Pair<Integer, Integer> photoSize = photoStorage.uploadPhoto(fileNamePrefix, photoFileStream);

            Photo photo = new Photo();
            photo.setId(photoId);
            photo.setWidth(photoSize.getLeft());
            photo.setHeight(photoSize.getRight());

            return photo;
        }
    }

}
