package co.katoonyaka.web.client.controllers;

import co.katoonyaka.web.client.controllers.dto.ClientResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public abstract class AbstractClientController {

    @Autowired
    private FreeMarkerViewResolver viewResolver;

    @RequestMapping
    public String htmlContent(Model model, HttpServletRequest request) throws Exception {
        ClientResponseData responseData = getResponseData(model, request);
        model.addAttribute("responseData", responseData);
        return "katoonyaka-template";
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ClientResponseData jsonContent(Model model, HttpServletRequest request) throws Exception {
        return getResponseData(model, request);
    }

    protected abstract void populateModel(Model model, Map pathVariables);

    protected abstract String getTitle(Model model, HttpServletRequest request);

    protected abstract String getContentTemplateName();

    private ClientResponseData getResponseData(Model model, HttpServletRequest request) throws Exception {
        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        populateModel(model, pathVariables);


        View resolvedView = viewResolver.resolveViewName(getContentTemplateName(), request.getLocale());
        MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        resolvedView.render(model.asMap(), request, mockResponse);
        String htmlContent = mockResponse.getContentAsString();

        String title = getTitle(model, request);
        return new ClientResponseData("Katoonyaka. " + title, htmlContent);
    }

}
