package co.katoonyaka.web.client.controllers;

import co.katoonyaka.domain.Handiwork;
import co.katoonyaka.services.CoverRepository;
import co.katoonyaka.services.HandiworkRepository;
import co.katoonyaka.web.client.domain.EnrichedHandiwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ClientController {

    @Autowired
    protected HandiworkRepository handiworkRepository;

    @Autowired
    protected CoverRepository coverRepository;

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("handiworks", handiworkRepository.findAllPublished());
        model.addAttribute("covers", coverRepository.findAllPublished());
        return "katoonyaka-template";
    }

    @RequestMapping("/portfolio/{link}")
    public String details(@PathVariable String link, Model model) {
        if (link.contains("_")) {
            return "redirect:/portfolio/" + link.replaceAll("_", "-");
        } else {
            Handiwork handiwork = handiworkRepository.findByUrl(link);

            if (handiwork == null) {
                return "redirect:/";
            }

            model.addAttribute("handiwork", new EnrichedHandiwork(handiwork));
            return "details";
        }
    }

}
