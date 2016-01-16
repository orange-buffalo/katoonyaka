package co.katoonyaka.web.client.controllers;

import co.katoonyaka.web.client.domain.ClientResponseModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/about")
public class AboutClientController extends AbstractClientController<ClientResponseModel> {

    @Override
    protected ClientResponseModel getModel(Map pathVariables) {
        //todo meta description, url, image
        return new ClientResponseModel("About Us", null, null, null);
    }

    @Override
    protected String getContentTemplate() {
        return "/about/about-content";
    }

}
