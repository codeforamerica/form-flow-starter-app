package org.formflowstartertemplate.app.interceptors;

import formflow.library.ScreenController;
import formflow.library.config.FlowConfiguration;
import formflow.library.exceptions.LandmarkNotSetException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * This interceptor redirects users to the configured screen if a flow is marked as disabled.
 */
@Component
// TODO: once format of value is locked down, make an applicable conditional
//@ConditionalOnProperty(name = "form-flow.disabled-flows", havingValue = "*")
public class DisabledFlowInterceptor implements HandlerInterceptor, Ordered {
  public static final String PATH_FORMAT = ScreenController.FLOW + "/" + ScreenController.FLOW_SCREEN_PATH;

  List<FlowConfiguration> flowConfigurations;
  String[] disabledFlows;

  public DisabledFlowInterceptor() {
    this.disabledFlows = new String[0];
  }

  public DisabledFlowInterceptor(List<FlowConfiguration> flowConfigurations, String[] disabledFlows) {
    this.flowConfigurations = flowConfigurations;
    this.disabledFlows = disabledFlows;
  }

  @Override
  public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws LandmarkNotSetException {
    System.out.println("Disabled flows is equal to: " + Arrays.toString(disabledFlows));

    Map<String, String> parsedUrl = new AntPathMatcher().extractUriTemplateVariables(PATH_FORMAT, request.getRequestURI());
    String requestedFlow = parsedUrl.get("flow");
    if (Arrays.asList(disabledFlows).contains(requestedFlow)) {
      FlowConfiguration flowConfiguration = flowConfigurations.stream()
              .filter(fc -> fc.getName().equals(parsedUrl.get("flow")))
              .findFirst()
              .orElse(null);

      if (flowConfiguration == null ||
              flowConfiguration.getLandmarks() == null ||
              flowConfiguration.getLandmarks().getDisabledFlowRedirect() == null) {
        throw new LandmarkNotSetException(
                "Flow %s has been disabled with no 'disabledFlowRedirect'".formatted(requestedFlow));
      }

      String disabledFlowRedirect = flowConfiguration.getLandmarks().getDisabledFlowRedirect();
      System.out.printf("Redirecting to %s - flow %s is disabled%n", disabledFlowRedirect, requestedFlow);

      try {
        response.sendRedirect(disabledFlowRedirect);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      return false;
    }
    return true;
  }

  @Override
  public int getOrder() {
    return HIGHEST_PRECEDENCE;
  }
}
