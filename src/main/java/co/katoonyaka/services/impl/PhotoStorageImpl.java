package co.katoonyaka.services.impl;

import co.katoonyaka.domain.PhotoSizesConfig;
import co.katoonyaka.services.ConfigService;
import co.katoonyaka.services.PhotoStorage;
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
import java.util.SortedSet;
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
public class PhotoStorageImpl implements PhotoStorage {

    private final ConfigService configService;

    private File photosDir;

    @Autowired
    public PhotoStorageImpl(ConfigService configService) {
        this.configService = configService;

        photosDir = new File(System.getenv("KATOONYAKA_HOME") + "/photos");

        if (!photosDir.exists()) {
            throw new IllegalStateException("Photos directory does not exist");
        }

        if (!photosDir.isDirectory()) {
            throw new IllegalStateException("Photos directory is not a directory");
        }
    }

    @Override
    public Pair<Integer, Integer> uploadPhoto(String fileNamePrefix, InputStream largeJpegStream) {
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

}
