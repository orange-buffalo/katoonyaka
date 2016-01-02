package co.katoonyaka.web.client.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/about")
public class AboutClientController extends AbstractClientController {

    @Override
    protected void populateModel(Model model, Map pathVariables) {

    }

    @Override
    protected String getTitle(Model model, HttpServletRequest request) {
        return "About Us";
    }

    @Override
    protected String getContentTemplateName() {
        return "about";
    }

}
