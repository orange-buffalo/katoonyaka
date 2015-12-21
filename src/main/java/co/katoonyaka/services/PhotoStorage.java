package co.katoonyaka.services;

import co.katoonyaka.domain.Photo;

import java.io.OutputStream;
import java.util.Collection;

public interface PhotoStorage {
    void loadPhoto(Photo photo, OutputStream stream, Integer width, Integer height);
    void removePhoto(String photo);
    Collection<String> getRegisteredExternalIds();
}
