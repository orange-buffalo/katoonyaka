package co.katoonyaka.web;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

import javax.servlet.Filter;

public class KatoonyakaInitializer extends AbstractDispatcherServletInitializer {

    @Override
    protected WebApplicationContext createServletApplicationContext() {
        XmlWebApplicationContext context = new XmlWebApplicationContext();
        context.setConfigLocation("/WEB-INF/spring/web-context.xml");
        return context;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{
                "/"
        };
    }

    @Override
    protected WebApplicationContext createRootApplicationContext() {
        return null;
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{
                new HiddenHttpMethodFilter()
        };
    }

}
