package org.formflowstartertemplate.app;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticPageController {

  @GetMapping("/")
  String getIndex(
      HttpSession httpSession
  ) {
    // For dev, reset session if you visit home
    httpSession.invalidate();

    return "index";
  }

  @GetMapping("/faq")
  String getFaq() {
    return "faq";
  }

  @GetMapping("/icons")
  String getIcons() {
    return "/fragments/icons";
  }

}
