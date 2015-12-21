package co.katoonyaka.web.admin.controllers;

import co.katoonyaka.domain.Handiwork;
import co.katoonyaka.services.HandiworkRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/admin/api/handiworks")
@ResponseBody
public class HandiworkController {

    @Autowired
    private HandiworkRepository handiworkRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Handiwork> getHandiworks() {
        return handiworkRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Handiwork addHandiwork(@RequestBody Handiwork handiwork) {
        if (StringUtils.isNotEmpty(handiwork.getId())) {
            throw new IllegalStateException("Invalid input");
        }

        handiworkRepository.save(handiwork);

        return handiwork;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void saveHandiworks(@RequestBody List<Handiwork> handiworks) {
        handiworkRepository.replaceAll(handiworks);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Handiwork getHandiwork(@PathVariable String id) {
        return handiworkRepository.findById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteHandiwork(@PathVariable String id) {
        handiworkRepository.delete(handiworkRepository.findById(id));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Handiwork updateHandiwork(@PathVariable String id,
                                     @RequestBody Handiwork handiwork) {
        if (!StringUtils.equals(handiwork.getId(), id)) {
            throw new IllegalStateException("Invalid input");
        }

        handiworkRepository.save(handiwork);

        return handiwork;
    }

}
