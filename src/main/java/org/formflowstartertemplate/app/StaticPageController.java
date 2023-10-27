package org.formflowstartertemplate.app;

import formflow.library.config.DisabledFlowPropertyConfiguration;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

/**
 * A controller to render static pages that are not in any flow.
 */
@Controller
public class StaticPageController {
  
  DisabledFlowPropertyConfiguration disabledFlowPropertyConfiguration;

  public StaticPageController(DisabledFlowPropertyConfiguration disabledFlowPropertyConfiguration) {
    this.disabledFlowPropertyConfiguration = disabledFlowPropertyConfiguration;
  }

  /**
   * Renders the website index page.
   *
   * @param httpSession The current HTTP session, not null.
   * @return the static page template.
   */
  @GetMapping("/")
  ModelAndView getIndex(HttpSession httpSession) {
    httpSession.invalidate(); // For dev, reset session if you visit home

    HashMap<String, Object> model = new HashMap<>();
    model.put("ubiEnabled", disabledFlowPropertyConfiguration == null || 
        !disabledFlowPropertyConfiguration.isFlowDisabled("ubi"));
    model.put("docUploadEnabled", disabledFlowPropertyConfiguration == null || 
        !disabledFlowPropertyConfiguration.isFlowDisabled("docUpload"));
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
