package co.katoonyaka.application;

import co.katoonyaka.utils.KatoonyakaObjectMapper;
import co.katoonyaka.web.admin.interceptors.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    SecurityInterceptor securityInterceptor;

    @Autowired
    KatoonyakaObjectMapper objectMapper;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor)
                .addPathPatterns("/admin/**");
    }

//    @Bean
//    public HttpMessageConverters customConverters() {
//        HttpMessageConverter<?> jackson2HttpMessageConverter =
//                new MappingJackson2HttpMessageConverter(objectMapper);
//        return new HttpMessageConverters(jackson2HttpMessageConverter);
//    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/admin/", ".html").cache(false);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/public/static/")
                .setCachePeriod(604800);

        registry.addResourceHandler("/admin/**")
                .addResourceLocations("classpath:/public/admin/")
                .setCachePeriod(0);
    }

}
