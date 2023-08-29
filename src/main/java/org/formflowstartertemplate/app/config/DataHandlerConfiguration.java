package org.formflowstartertemplate.app.config;

import formflow.library.data.SubmissionRepositoryService;
import java.util.List;
import org.formflowstartertemplate.app.interceptor.DataRequiredInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class DataHandlerConfiguration implements WebMvcConfigurer {
  @Autowired
  SubmissionRepositoryService submissionRepositoryService;
  @Override
  public void addInterceptors(InterceptorRegistry registry){
    registry.addInterceptor(new DataRequiredInterceptor(this.submissionRepositoryService)).addPathPatterns(List.of(DataRequiredInterceptor.FLOW_PATH_FORMAT, DataRequiredInterceptor.NAVIGATION_FLOW_PATH_FORMAT));
  }
}
