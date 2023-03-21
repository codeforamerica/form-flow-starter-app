package org.formflowstartertemplate.app;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
  String getIndex(HttpSession httpSession) {
    httpSession.invalidate(); // For dev, reset session if you visit home
    return "index";
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

  @GetMapping("/privacy")
  String getPrivacy() {
    return "privacy";
  }
}
