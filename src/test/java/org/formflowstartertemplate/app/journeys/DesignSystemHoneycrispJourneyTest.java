package org.formflowstartertemplate.app.journeys;

import static org.assertj.core.api.Assertions.assertThat;

import org.formflowstartertemplate.app.utils.AbstractBasePageTest;
import org.formflowstartertemplate.app.utils.Page;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;

@Tag("staticPagesJourney")
@Disabled
@TestPropertySource(properties = {
    "form-flow.design-system.name=honeycrisp",
})
public class DesignSystemHoneycrispJourneyTest extends AbstractBasePageTest {

  protected void initTestPage() {
    testPage = new Page(driver);
  }

  @Autowired
  Environment environment;

  @Test
  void staticPagesJourney() {
    assertThat(testPage.getTitle()).isEqualTo("Apply for UBI payments easily online.");

    WebElement link = driver.findElement(By.cssSelector("link[href*='/assets/css/custom.css']"));
    assertThat(link).isNotNull();
    String hrefValue = link.getAttribute("href");
    assertThat(hrefValue.contains("/assets/css/styles.css")).isTrue();

    WebElement script = driver.findElement(By.cssSelector("script[src*='/assets/js/scripts.js']"));
    assertThat(script).isNotNull();
    String srcValue = script.getAttribute("src");
    assertThat(srcValue.contains("/assets/js/scripts.js")).isTrue();
  }
}
