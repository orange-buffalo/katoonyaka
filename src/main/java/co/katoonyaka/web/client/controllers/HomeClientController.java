package co.katoonyaka.web.client.controllers;

import co.katoonyaka.services.CoverRepository;
import co.katoonyaka.services.HandiworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/")
public class HomeClientController extends AbstractClientController {

    @Autowired
    protected HandiworkRepository handiworkRepository;

    @Autowired
    protected CoverRepository coverRepository;

    @Override
    protected void populateModel(Model model, Map pathVariables) {
        model.addAttribute("handiworks", handiworkRepository.findAllPublished());
        model.addAttribute("covers", coverRepository.findAllPublished());
    }

    @Override
    protected String getTitle(Model model, HttpServletRequest request) {
        return "Portfolio";
    }

    @Override
    protected String getContentTemplateName() {
        return "home";
    }

}
