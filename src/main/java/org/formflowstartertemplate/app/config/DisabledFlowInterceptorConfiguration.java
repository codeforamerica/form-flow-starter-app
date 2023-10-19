package org.formflowstartertemplate.app.config;

import java.util.List;

import formflow.library.ScreenController;
import org.formflowstartertemplate.app.interceptors.DisabledFlowInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/***
 * Adds DisabledFlowInterceptorConfiguration to the Interceptor registry.
 */
@Configuration
public class DisabledFlowInterceptorConfiguration implements WebMvcConfigurer {

  private final String[] disabledFlows;

  public DisabledFlowInterceptorConfiguration(@Value("${form-flow.disabled-flows}") String[] disabledFlows) {
    this.disabledFlows = disabledFlows;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new DisabledFlowInterceptor(disabledFlows))
        .addPathPatterns(List.of(ScreenController.FLOW + "/" + ScreenController.FLOW_SCREEN_PATH));
  }
}
