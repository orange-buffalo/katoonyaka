package co.katoonyaka.web.client.controllers;

import co.katoonyaka.services.PhotoStorage;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/photos/")
public class PhotoClientController {

    private final PhotoStorage photoStorage;

    @Autowired
    public PhotoClientController(PhotoStorage photoStorage) {
        this.photoStorage = photoStorage;
    }

    @RequestMapping(value = "{fileName:.+}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void getPhoto(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        response.setHeader("cache-control", "public, max-age=31536000");
        OutputStream stream = response.getOutputStream();
        photoStorage.downloadPhoto(fileName, stream);
        stream.flush();
        stream.close();
    }

}
