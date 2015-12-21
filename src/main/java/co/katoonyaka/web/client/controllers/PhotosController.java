package co.katoonyaka.web.client.controllers;

import co.katoonyaka.domain.Handiwork;
import co.katoonyaka.domain.Photo;
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
public class PhotosController {

    @Autowired
    private HandiworkRepository handiworkRepository;

    @Autowired
    private PhotoStorage photoStorage;

    @RequestMapping(value = "{handiworkUrl:[A-Za-z0-9-]+}.{photoId:[A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z]}.jpeg",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public void loadPhoto(@PathVariable String handiworkUrl,
                          @PathVariable String photoId,
                          HttpServletResponse response) throws IOException {
        loadPhoto(handiworkUrl, photoId, null, null, response);
    }

    @RequestMapping(value = "{handiworkUrl:[A-Za-z0-9-]+}/{photoId:[A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z]}/{width:[0-9]+}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public void loadPhoto(@PathVariable String handiworkUrl,
                          @PathVariable String photoId,
                          @PathVariable Integer width,
                          HttpServletResponse response) throws IOException {
        loadPhoto(handiworkUrl, photoId, width, null, response);
    }

    @RequestMapping(value = "{handiworkUrl:[A-Za-z0-9-]+}/{photoId:[A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z]}/{width:[0-9]+}x{height:[0-9]+}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public void loadPhoto(@PathVariable String handiworkUrl,
                          @PathVariable String photoId,
                          @PathVariable Integer width,
                          @PathVariable Integer height,
                          HttpServletResponse response) throws IOException {
        Handiwork handiwork = handiworkRepository.findByUrl(handiworkUrl);
        Photo photo = handiwork.getPhoto(photoId);
        response.setHeader("cache-control", "public, max-age=3600");
        OutputStream stream = response.getOutputStream();
        photoStorage.loadPhoto(photo, stream, width, height);
        stream.flush();
        stream.close();
    }

}
