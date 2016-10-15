package co.katoonyaka.services.impl;

import co.katoonyaka.ApplicationException;
import co.katoonyaka.domain.Photo;
import co.katoonyaka.domain.PhotoSizesConfig;
import co.katoonyaka.domain.uc.UcFile;
import co.katoonyaka.domain.uc.UcPage;
import co.katoonyaka.services.ConfigService;
import co.katoonyaka.services.PhotoStorage;
import com.google.common.collect.Collections2;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.twelvemonkeys.image.ResampleOp;
import com.twelvemonkeys.imageio.plugins.jpeg.JPEGImageReader;
import com.twelvemonkeys.imageio.plugins.jpeg.JPEGImageWriter;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import java.util.SortedSet;
import javax.annotation.PostConstruct;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UploadCarePhotoStorage implements PhotoStorage {

    private static final int MAX_UPLOADCARE_SIZE = 2048;

    @Autowired
    private ConfigService configService;

    private String publicKey;
    private String privateKey;

    private File photosDir;

    @PostConstruct
    private void init() {
        publicKey = configService.getConfigValue("uploadcare.publicKey");
        privateKey = configService.getConfigValue("uploadcare.privateKey");

        photosDir = new File(System.getProperty("KATOONYAKA_HOME") + "/photos");

        if (!photosDir.exists()) {
            throw new IllegalStateException("Photos directory does not exist");
        }

        if (!photosDir.isDirectory()) {
            throw new IllegalStateException("Photos directory is not a directory");
        }
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
        }
        catch (Exception e) {
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
        }
        catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public Collection<String> getRegisteredExternalIds() {
        List<UcFile> files = new ArrayList<>();
        loadNextPageOfFiles("https://api.uploadcare.com/files/", files);
        return Collections2.transform(files, UcFile::getUuid);
    }

    @Override
    public Pair uploadPhoto(String fileNamePrefix, InputStream largeJpegStream) {
        try {
            PhotoSizesConfig photoSizesConfig = configService.getPhotoSizesConfig();
            SortedSet<PhotoSizesConfig.PhotoSize> photoSizes = photoSizesConfig.getSizes();

            PhotoSizesConfig.PhotoSize largePhotoSize = photoSizes.last();
            File largePhotoFile = new File(photosDir, String.format("%s%s%s.jpeg",
                    fileNamePrefix, photoSizesConfig.getNameSeparator(), largePhotoSize.getName()));
            try (OutputStream photoOutputStream = new FileOutputStream(largePhotoFile)) {
                IOUtils.copy(largeJpegStream, photoOutputStream);
            }

            Pair<BufferedImage, IIOMetadata> largePhotoData = readPhoto(largePhotoFile);
            BufferedImage largePhotoImage = largePhotoData.getLeft();
            IIOMetadata photoMetadata = largePhotoData.getRight();

            photoSizes.stream()
                    .filter(photoSize -> !photoSize.equals(largePhotoSize))
                    .forEachOrdered(photoSize -> {
                        try {
                            writePhoto(
                                    largePhotoImage,
                                    photoMetadata,
                                    photoSize,
                                    fileNamePrefix + photoSizesConfig.getNameSeparator()
                            );
                        }
                        catch (IOException e) {
                            throw new IllegalStateException(e);
                        }
                    });

            return Pair.of(largePhotoImage.getWidth(), largePhotoImage.getHeight());
        }
        catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void downloadPhoto(String fileName, OutputStream photoStream) {
        File photoFile = new File(photosDir, fileName);
        try (InputStream sourceStream = new FileInputStream(photoFile)) {
            IOUtils.copy(sourceStream, photoStream);
        }
        catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void writePhoto(BufferedImage originalPhotoImage,
                            IIOMetadata originalPhotoMetadata,
                            PhotoSizesConfig.PhotoSize photoSize,
                            String fileNamePrefix) throws IOException {

        int originalWidth = originalPhotoImage.getWidth();
        int originalHeight = originalPhotoImage.getHeight();

        int targetWidth = photoSize.getWidthInPx();
        targetWidth = (targetWidth > originalWidth) ? originalWidth : targetWidth;

        float photoAspectRatio = originalWidth / (float) originalHeight;
        int targetHeight = Math.round(targetWidth / photoAspectRatio);

        BufferedImageOp resampler = new ResampleOp(targetWidth, targetHeight, ResampleOp.FILTER_LANCZOS);
        BufferedImage outputImage = resampler.filter(originalPhotoImage, null);

        ImageWriter imageWriter = ImageIO.getImageWritersByFormatName("jpeg").next();
        if (!(imageWriter instanceof JPEGImageWriter)) {
            throw new IllegalStateException("Wrong writer detected: " + imageWriter);
        }

        File targetFile = new File(photosDir, String.format("%s%s.jpeg", fileNamePrefix, photoSize.getName()));
        try (ImageOutputStream output = ImageIO.createImageOutputStream(targetFile)) {
            imageWriter.setOutput(output);

            ImageWriteParam writeParam = imageWriter.getDefaultWriteParam();
            writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            writeParam.setCompressionQuality(0.9f);
            writeParam.setProgressiveMode(ImageWriteParam.MODE_DEFAULT);

            imageWriter.write(originalPhotoMetadata, new IIOImage(outputImage, null, originalPhotoMetadata), writeParam);
        }
        finally {
            imageWriter.dispose();
        }
    }

    private Pair<BufferedImage, IIOMetadata> readPhoto(File photoFile) throws IOException {
        try (ImageInputStream input = ImageIO.createImageInputStream(photoFile)) {

            ImageReader reader = ImageIO.getImageReaders(input).next();
            if (!(reader instanceof JPEGImageReader)) {
                throw new IllegalStateException("Wrong reader detected: " + reader);
            }

            try {
                reader.setInput(input);
                IIOMetadata sourceImageMetadata = reader.getImageMetadata(0);
                BufferedImage sourceImage = reader.read(0);
                return Pair.of(sourceImage, sourceImageMetadata);
            }
            finally {
                reader.dispose();
            }
        }
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

        }
        catch (Exception e) {
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
        }
        finally {
            connection.disconnect();
        }
    }

}
