package org.formflowstartertemplate.app.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * This interceptor redirects users to the configured screen if a flow is marked as disabled.
 */
@Component
// TODO: once format of value is locked down, make an applicable conditional
//@ConditionalOnProperty(name = "form-flow.disabled-flows", havingValue = "*")
public class DisabledFlowInterceptor implements HandlerInterceptor, Ordered {
  String[] disabledFlows;

  public DisabledFlowInterceptor() {
    this.disabledFlows = new String[0];
  }

  public DisabledFlowInterceptor(String[] disabledFlows) {
    this.disabledFlows = disabledFlows;
  }

  @Override
  public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
    System.out.println("Disabled flows is equal to: " + Arrays.toString(disabledFlows));
    return true;
  }

  @Override
  public int getOrder() {
    return HIGHEST_PRECEDENCE;
  }
}
