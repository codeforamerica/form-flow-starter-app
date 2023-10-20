package org.formflowstartertemplate.app;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashMap;

/**
 * A controller to render static pages that are not in any flow.
 */
@Controller
public class StaticPageController {

  @Value("${form-flow.disabled-flows}")
  String[] disabledFlows;

  /**
   * Renders the website index page.
   *
   * @param httpSession The current HTTP session, not null
   * @return the static page template
   */
  @GetMapping("/")
  ModelAndView getIndex(HttpSession httpSession) {
    httpSession.invalidate(); // For dev, reset session if you visit home

    HashMap<String, Object> model = new HashMap<>();
    model.put("ubiEnabled", !Arrays.asList(disabledFlows).contains("ubi"));
    model.put("docUploadEnabled", !Arrays.asList(disabledFlows).contains("docUpload"));
    return new ModelAndView("index", model);
  }

  /**
   * Renders the website faq page.
   *
   * @return the static page template
   */
  @GetMapping("/faq")
  String getFaq() {
    return "faq";
  }

  /**
   * Renders the website privacy page.
   *
   * @return the static page template
   */
  @GetMapping("/privacy")
  String getPrivacy() {
    return "privacy";
  }

  /**
   * Renders the page to redirect to when a flow is disabled.
   *
   * @return the static page template
   */
  @GetMapping("/disabledFeature")
  String getDisabled() {
    return "disabledFeature";
  }
}
