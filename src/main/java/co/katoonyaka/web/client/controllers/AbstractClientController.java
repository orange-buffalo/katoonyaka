package co.katoonyaka.web.client.controllers;

import co.katoonyaka.services.CoverRepository;
import co.katoonyaka.web.client.domain.ClientResponseData;
import co.katoonyaka.web.client.domain.ClientResponseModel;
import org.apache.commons.lang3.StringUtils;
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

public abstract class AbstractClientController<T extends ClientResponseModel> {

    @Autowired
    private FreeMarkerViewResolver viewResolver;

    @Autowired
    protected CoverRepository coverRepository;

    @RequestMapping
    public String getHtmlContent(Model model, HttpServletRequest request) throws Exception {
        ClientResponseData responseData = getResponseData(model, request);
        model.addAttribute("responseData", responseData);
        model.addAttribute("covers", coverRepository.findAllPublished());
        return "katoonyaka-template";
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ClientResponseData getJsonContent(Model model, HttpServletRequest request) throws Exception {
        return getResponseData(model, request);
    }

    protected abstract T getModel(Map pathVariables);

    protected abstract String getContentTemplate();

    protected String getAdditionalLinksTemplate() {
        return null;
    }

    private ClientResponseData getResponseData(Model templateModel, HttpServletRequest request) throws Exception {
        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        T clientResponseModel = getModel(pathVariables);
        templateModel.addAttribute("model", clientResponseModel);

        String mainContent = renderFreemarkerTemplate(templateModel, getContentTemplate(), request);
        String title = "Katoonyaka. " + clientResponseModel.getTitle();
        String additionalLinksContent = renderFreemarkerTemplate(templateModel, getAdditionalLinksTemplate(), request);

        return new ClientResponseData(title, mainContent, additionalLinksContent);
    }

    private String renderFreemarkerTemplate(Model model,
                                            String template,
                                            HttpServletRequest request) throws Exception {
        if (StringUtils.isNotEmpty(template)) {
            View resolvedView = viewResolver.resolveViewName(template, request.getLocale());
            MockHttpServletResponse mockResponse = new MockHttpServletResponse();
            resolvedView.render(model.asMap(), request, mockResponse);
            return mockResponse.getContentAsString();
        }
        return null;
    }

}
