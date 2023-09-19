package org.formflowstartertemplate.app;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HtmxController {

  @PostMapping("/htmx/validation")
  @HxRequest
  ModelAndView postHtmxValidation(
      @RequestParam(required = false) MultiValueMap<String, String> formData,
      HttpSession httpSession
  ) {
    var customValue = formData.get("custom").get(0);
    Map<String, Object> model = new HashMap<>();
    boolean doesValueHaveDigit = customValue.matches(".*\\d.*");

    if (doesValueHaveDigit) {
      model.put("error", "Error: no digits allowed");
    }
    model.put("value", formData.get("custom").get(0));
    return new ModelAndView("fragments/test-input.html", model);
  }

  @GetMapping("/htmx/emoji")
  @HxRequest
  ModelAndView getHtmxEmoji() {
    List<String> emojis = Arrays.asList("🐸", "💜", "🎆", "🦅", "🐟", "🎳", "🫥", "🥸", "🦖", "🐅");
    Random random = new Random();
    var index = random.nextInt(10);
    Map<String, Object> model = new HashMap<>();
    model.put("emoji", emojis.get(index));

    return new ModelAndView("fragments/emoji", model);
  }
}
