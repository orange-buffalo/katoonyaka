package co.katoonyaka.services;

import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.lang3.tuple.Pair;

public interface PhotoStorage {

    Pair<Integer, Integer> uploadPhoto(String fileNamePrefix, InputStream largeJpegStream);

    void downloadPhoto(String fileName, OutputStream photoStream);
}
