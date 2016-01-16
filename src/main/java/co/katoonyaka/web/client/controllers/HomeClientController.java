package co.katoonyaka.web.client.controllers;

import co.katoonyaka.domain.Cover;
import co.katoonyaka.services.CoverRepository;
import co.katoonyaka.services.HandiworkRepository;
import co.katoonyaka.web.client.domain.HomeResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/")
public class HomeClientController extends AbstractClientController<HomeResponseModel> {

    @Autowired
    private HandiworkRepository handiworkRepository;

    @Autowired
    private CoverRepository coverRepository;

    @Override
    protected HomeResponseModel getModel(Map pathVariables) {
        List<Cover> covers = coverRepository.findAllPublished();
        Cover cover = covers.get(new Random().nextInt(covers.size()));

        return new HomeResponseModel(
                "Portfolio",
                "Wearable self designed and entirely hand knitted wrist warmers, practical and stylish fingerless gloves.",
                "photos/cover/" + cover.getId() + ".jpeg",
                "",
                handiworkRepository.findAllPublished()
        );
    }

    @Override
    protected String getContentTemplate() {
        return "/home/home-content";
    }

}
