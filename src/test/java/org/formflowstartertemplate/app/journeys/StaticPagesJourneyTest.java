package org.formflowstartertemplate.app.journeys;

import static org.assertj.core.api.Assertions.assertThat;

import org.formflowstartertemplate.app.utils.AbstractBasePageTest;
import org.formflowstartertemplate.app.utils.Page;
import org.springframework.core.env.Environment;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Tag("staticPagesJourney")
public class StaticPagesJourneyTest extends AbstractBasePageTest {

  protected void initTestPage() {
    testPage = new Page(driver);
  }

  @Autowired
  Environment environment;

  @Test
  void staticPagesJourney() {
    assertThat(environment.getProperty("form-flow.disabled-flows")).isEqualTo(null);
    // Landing screen
    assertThat(testPage.getTitle()).isEqualTo("Apply for UBI payments easily online.");
    assertThat(driver.getWindowHandles().size()).isEqualTo(1);
    String originalWindow = driver.getWindowHandle();

    // Go to FAQ tab
    testPage.clickLink("FAQ");
    assertThat(driver.getWindowHandles().size()).isEqualTo(2);
    switchAwayFromOriginalWindow(originalWindow);
    assertThat(testPage.getTitle()).isEqualTo("Frequently Asked Questions");
    // Back on landing screen
    driver.close();
    driver.switchTo().window(originalWindow);
    assertThat(testPage.getTitle()).isEqualTo("Apply for UBI payments easily online.");
    assertThat(driver.getWindowHandles().size()).isEqualTo(1);
    // Go to privacy policy tab
    testPage.clickLink("Privacy Policy");
    assertThat(driver.getWindowHandles().size()).isEqualTo(2);
    switchAwayFromOriginalWindow(originalWindow);
    assertThat(testPage.getTitle()).isEqualTo("Privacy Policy");
  }

  void switchAwayFromOriginalWindow(String originalWindow) {
    for (String windowHandle : driver.getWindowHandles()) {
      if(!originalWindow.contentEquals(windowHandle)) {
        driver.switchTo().window(windowHandle);
        break;
      }
    }
  }
}
