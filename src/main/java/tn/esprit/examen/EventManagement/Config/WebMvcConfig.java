package tn.esprit.examen.EventManagement.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map /events/** to the physical folder in your project
        registry.addResourceHandler("/events/**")
                .addResourceLocations("file:src/main/resources/static/events/");
    }
}
