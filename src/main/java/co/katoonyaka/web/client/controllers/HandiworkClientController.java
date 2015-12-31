package co.katoonyaka.web.client.controllers;

import co.katoonyaka.domain.Handiwork;
import co.katoonyaka.services.HandiworkRepository;
import co.katoonyaka.web.client.domain.EnrichedHandiwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/portfolio/{link}")
public class HandiworkClientController extends AbstractClientController {

    @Autowired
    protected HandiworkRepository handiworkRepository;

    @Override
    protected void populateModel(Model model, Map pathVariables) {
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

        if (handiwork != null) {
            model.addAttribute("handiwork", new EnrichedHandiwork(handiwork));
        }
    }

    @Override
    protected String getTitle(Model model, HttpServletRequest request) {
        return ((EnrichedHandiwork) model.asMap().get("handiwork")).getName();
    }

    @Override
    protected String getContentTemplateName() {
        return "details";
    }

}
