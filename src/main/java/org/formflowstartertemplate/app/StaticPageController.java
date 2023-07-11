package org.formflowstartertemplate.app;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * A controller to render static pages that are not in any flow.
 */
@Controller
public class StaticPageController {

  /**
   * Renders the website index page.
   *
   * @param httpSession The current HTTP session, not null
   * @return the static page template
   */
  @GetMapping("/")
  ModelAndView getIndex(HttpSession httpSession) {
    httpSession.invalidate(); // For dev, reset session if you visit home

    Map<String, Object> model = new HashMap<>();
    model.put("screen", "/");

    return new ModelAndView("index", model);  }

  /**
   * Renders the website faq page.
   *
   * @return the static page template
   */
  @GetMapping("/faq")
  ModelAndView getFaq() {
    Map<String, Object> model = new HashMap<>();
    model.put("screen", "faq");

    return new ModelAndView("faq", model);
  }

  /**
   * Renders the website privacy page.
   *
   * @return the static page template
   */
  @GetMapping("/privacy")
  ModelAndView getPrivacy() {
    Map<String, Object> model = new HashMap<>();
    model.put("screen", "privacy");

    return new ModelAndView("privacy", model);
  }
}
