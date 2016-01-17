package co.katoonyaka.web.client.controllers;

import co.katoonyaka.domain.Cover;
import co.katoonyaka.domain.Handiwork;
import co.katoonyaka.domain.Photo;
import co.katoonyaka.services.CoverRepository;
import co.katoonyaka.services.HandiworkRepository;
import co.katoonyaka.services.PhotoStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/photos/")
public class PhotoClientController {

    private static final int DEFAULT_PHOTO_SIZE = 1000;

    @Autowired
    private HandiworkRepository handiworkRepository;

    @Autowired
    private PhotoStorage photoStorage;

    @Autowired
    CoverRepository coverRepository;

    @RequestMapping(value = "{handiworkUrl:[A-Za-z0-9-]+}.{photoId:[A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z]}.jpeg",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public void loadPhoto(@PathVariable String handiworkUrl,
                          @PathVariable String photoId,
                          HttpServletResponse response) throws IOException {
        Handiwork handiwork = handiworkRepository.findByUrl(handiworkUrl);
        Photo photo = handiwork.getPhoto(photoId);
        loadPhoto(photo, Math.min(DEFAULT_PHOTO_SIZE, photo.getWidth()), null, response);
    }

    @RequestMapping(value = "{handiworkUrl:[A-Za-z0-9-]+}/{photoId:[A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z]}/{width:[0-9]+}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    @Deprecated
    public void loadPhotoLegacy(@PathVariable String handiworkUrl,
                                @PathVariable String photoId,
                                @PathVariable Integer width,
                                HttpServletResponse response) throws IOException {
        loadPhoto(handiworkUrl, photoId, width, response);
    }

    @RequestMapping(value = "{handiworkUrl:[A-Za-z0-9-]+}.{photoId:[A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z]}.w{width:[0-9]+}.jpeg",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public void loadPhoto(@PathVariable String handiworkUrl,
                          @PathVariable String photoId,
                          @PathVariable Integer width,
                          HttpServletResponse response) throws IOException {
        loadPhoto(handiworkUrl, photoId, width, null, response);
    }

    @RequestMapping(value = "{handiworkUrl:[A-Za-z0-9-]+}/{photoId:[A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z]}/{width:[0-9]+}x{height:[0-9]+}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    @Deprecated
    public void loadPhotoLegacy(@PathVariable String handiworkUrl,
                                @PathVariable String photoId,
                                @PathVariable Integer width,
                                @PathVariable Integer height,
                                HttpServletResponse response) throws IOException {
        loadPhoto(handiworkUrl, photoId, width, height, response);
    }

    @RequestMapping(value = "{handiworkUrl:[A-Za-z0-9-]+}.{photoId:[A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z]}.{width:[0-9]+}x{height:[0-9]+}.jpeg",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public void loadPhoto(@PathVariable String handiworkUrl,
                          @PathVariable String photoId,
                          @PathVariable Integer width,
                          @PathVariable Integer height,
                          HttpServletResponse response) throws IOException {
        Handiwork handiwork = handiworkRepository.findByUrl(handiworkUrl);
        Photo photo = handiwork.getPhoto(photoId);
        loadPhoto(photo, width, height, response);
    }

    @RequestMapping(value = "/cover/{coverId}.jpeg",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public void loadPhoto(@PathVariable String coverId,
                          HttpServletResponse response) throws IOException {
        Cover cover = coverRepository.findById(coverId);
        Photo photo = cover.getPhoto();
        loadPhoto(photo, Math.min(DEFAULT_PHOTO_SIZE, photo.getWidth()), null, response);
    }

    private void loadPhoto(Photo photo,
                           Integer width,
                           Integer height,
                           HttpServletResponse response) throws IOException {
        response.setHeader("cache-control", "public, max-age=31536000");
        OutputStream stream = response.getOutputStream();
        photoStorage.loadPhoto(photo, stream, width, height);
        stream.flush();
        stream.close();
    }

}
