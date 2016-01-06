package co.katoonyaka.web.client.controllers;

import co.katoonyaka.domain.Handiwork;
import co.katoonyaka.services.HandiworkRepository;
import co.katoonyaka.web.client.domain.HandiworkResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/portfolio/{link}")
public class HandiworkClientController extends AbstractClientController<HandiworkResponseModel> {

    @Autowired
    protected HandiworkRepository handiworkRepository;

    @Override
    protected HandiworkResponseModel getModel(Map pathVariables) {
        // todo redirects
//         if (link.contains("_")) {
//            return "redirect:/portfolio/" + link.replaceAll("_", "-");
//        } else {
//             ...
//             if (handiwork == null) {
//                return "redirect:/";
//            }
//         }

        String link = (String) pathVariables.get("link");
        Handiwork handiwork = handiworkRepository.findByUrl(link);

        if (handiwork == null) {
            throw new IllegalStateException("Cannot find handiwork by " + link);
        }

        HandiworkResponseModel model = new HandiworkResponseModel(handiwork);

        List<Handiwork> allHandiworks = handiworkRepository.findAllPublished();
        int index = allHandiworks.indexOf(handiwork);

        if (index > 0) {
            model.setPreviousHandiwork(allHandiworks.get(index - 1));
        }

        if (index < allHandiworks.size() - 1) {
            model.setNextHandiwork(allHandiworks.get(index + 1));
        }

        return model;
    }

    @Override
    protected String getContentTemplate() {
        return "/handiwork/handiwork-content";
    }

    @Override
    protected String getAdditionalLinksTemplate() {
        return "/handiwork/handiwork-links";
    }

}
