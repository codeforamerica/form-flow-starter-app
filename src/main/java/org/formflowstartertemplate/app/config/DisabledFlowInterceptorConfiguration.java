package org.formflowstartertemplate.app.config;

import java.util.List;

import formflow.library.config.FlowConfiguration;
import org.formflowstartertemplate.app.interceptors.DisabledFlowInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.formflowstartertemplate.app.interceptors.DisabledFlowInterceptor.PATH_FORMAT;

/***
 * Adds DisabledFlowInterceptorConfiguration to the Interceptor registry.
 */
@Configuration
public class DisabledFlowInterceptorConfiguration implements WebMvcConfigurer {

  @Autowired
  List<FlowConfiguration> flowConfigurations;
  private final String[] disabledFlows;

  public DisabledFlowInterceptorConfiguration(@Value("${form-flow.disabled-flows}") String[] disabledFlows) {
    this.disabledFlows = disabledFlows;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new DisabledFlowInterceptor(flowConfigurations, disabledFlows))
        .addPathPatterns(List.of(PATH_FORMAT));
  }
}
