package co.katoonyaka.web.client.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/shops")
public class ShopsClientController extends AbstractClientController {

    @Override
    protected void populateModel(Model model, Map pathVariables) {

    }

    @Override
    protected String getTitle(Model model, HttpServletRequest request) {
        return "Our Shops";
    }

    @Override
    protected String getContentTemplateName() {
        return "shops";
    }

}
