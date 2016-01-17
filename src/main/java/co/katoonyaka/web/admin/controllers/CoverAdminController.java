package co.katoonyaka.web.admin.controllers;

import co.katoonyaka.domain.Cover;
import co.katoonyaka.services.CoverRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/api/covers")
public class CoverAdminController {

    @Autowired
    private CoverRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Cover> getCovers() {
        return repository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Cover newCover(@RequestBody Cover cover) {
        if (cover.getId() != null) {
            throw new IllegalArgumentException("Cover should be new");
        }
        repository.save(cover);
        return cover;
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
