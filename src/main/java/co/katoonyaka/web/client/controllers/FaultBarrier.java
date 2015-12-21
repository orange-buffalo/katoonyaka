package co.katoonyaka.web.client.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FaultBarrier {

    @RequestMapping(value = "/crash", method = RequestMethod.GET)
    public String crash(HttpServletRequest request,
                        Model model) {
        Integer errorCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (errorCode == null) {
            errorCode = 500;
        }

        String message = "Ooops, looks like we failed to process your request.<br/>Sorry :(";
        if (errorCode == 404) {
            message = "Hmmm, this page does not exist.<br/>Did you type it wrongly or we moved it?";
        } else if (errorCode == 403) {
            message = "No way, this area is for katoonyaka stuff only";
        }

        model.addAttribute("errorMessage", message);
        model.addAttribute("errorCode", errorCode);

        return "crash";
    }

}
