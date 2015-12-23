package co.katoonyaka.web.admin.controllers;

import co.katoonyaka.services.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private ConfigService configService;

    @RequestMapping(method = RequestMethod.GET)
    public String adminHome() {
       return "admin";
    }

    @RequestMapping(value = "/oauth")
    public void oauth() {
        log.info("auth callback");
    }

    @RequestMapping(value = "/config/{configProperty:.+}")
    @ResponseBody
    public Object getConfigValue(@PathVariable String configProperty) {
        return configService.getConfigValue(configProperty);
    }

}
