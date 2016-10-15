package co.katoonyaka.services;

import co.katoonyaka.domain.Photo;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import org.apache.commons.lang3.tuple.Pair;

public interface PhotoStorage {
    @Deprecated
    void loadPhoto(Photo photo, OutputStream stream, Integer width, Integer height);
    void removePhoto(String photo);
    Collection<String> getRegisteredExternalIds();

    Pair uploadPhoto(String fileNamePrefix, InputStream largeJpegStream);

    void downloadPhoto(String fileName, OutputStream photoStream);
}
