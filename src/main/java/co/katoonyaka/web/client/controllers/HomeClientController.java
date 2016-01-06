package co.katoonyaka.web.client.controllers;

import co.katoonyaka.services.HandiworkRepository;
import co.katoonyaka.web.client.domain.HomeResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/")
public class HomeClientController extends AbstractClientController<HomeResponseModel> {

    @Autowired
    protected HandiworkRepository handiworkRepository;

    @Override
    protected HomeResponseModel getModel(Map pathVariables) {
        return new HomeResponseModel(
                "Portfolio",
                handiworkRepository.findAllPublished()
        );
    }

    @Override
    protected String getContentTemplate() {
        return "/home/home-content";
    }

}
