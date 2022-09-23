package org.formflowstartertemplate.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
//    // Tried this approach
//    registry
//        .addResourceHandler("/webjars/**")
//        .addResourceLocations("/webjars/");

    String[] CLASSPATH_RESOURCE_LOCATIONS = {
        "classpath:/META-INF/resources/",
        "classpath:/resources/",
        "classpath:/static/",
        "classpath:/public/"};


    // Does this work?
    registry.addResourceHandler("/**")
        .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    registry.addResourceHandler("/webjars/**")
        .addResourceLocations("/webjars/").resourceChain(false);
  }
}
