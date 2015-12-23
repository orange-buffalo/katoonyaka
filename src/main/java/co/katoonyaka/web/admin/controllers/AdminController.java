package co.katoonyaka.web.admin.controllers;

import co.katoonyaka.services.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(value = "/api/config/{configProperty:.+}", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public Object getConfigValue(@PathVariable String configProperty) {
        return configService.getConfigValue(configProperty);
    }

}
