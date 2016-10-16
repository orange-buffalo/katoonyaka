package co.katoonyaka.web.admin.controllers;

import co.katoonyaka.domain.Cover;
import co.katoonyaka.services.CoverRepository;
import co.katoonyaka.services.IdGenerator;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/api/covers")
public class CoverAdminController {

    private final CoverRepository repository;

    private final PhotoUploadHelper photoUploadHelper;

    private final IdGenerator idGenerator;

    @Autowired
    public CoverAdminController(CoverRepository repository,
                                PhotoUploadHelper photoUploadHelper,
                                IdGenerator idGenerator) {
        this.repository = repository;
        this.photoUploadHelper = photoUploadHelper;
        this.idGenerator = idGenerator;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Cover> getCovers() {
        return repository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addCover(@RequestParam("file") MultipartFile coverPhotoFile) throws IOException {
        Cover cover = new Cover();
        // TODO get rid of this by moving to normal storage
        repository.save(cover);
        cover.setPhoto(photoUploadHelper.uploadPhoto(
                coverPhotoFile, Collections.emptyList(), photoId -> "cover." + cover.getId())
        );
        repository.save(cover);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void replaceCovers(@RequestBody List<Cover> covers) {
        repository.replaceAll(covers);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Cover getCover(@PathVariable String id) {
        return repository.findById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Cover saveCover(@PathVariable String id,
                           @RequestBody Cover cover) {
        if (!StringUtils.equals(id, cover.getId())) {
            throw new IllegalArgumentException("Wrong data submitted");
        }

        repository.save(cover);

        return cover;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteCover(@PathVariable String id) {
        repository.delete(repository.findById(id));
    }

}
