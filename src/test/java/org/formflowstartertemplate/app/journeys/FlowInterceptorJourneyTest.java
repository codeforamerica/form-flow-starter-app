package org.formflowstartertemplate.app.journeys;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import org.formflowstartertemplate.app.utils.AbstractBasePageTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;

public class FlowInterceptorJourneyTest extends AbstractBasePageTest {

  @Test
  void testInvalidatingSessionWhenSessionIsLost() {
    assertThat(testPage.getTitle()).isEqualTo("Apply for UBI payments easily online.");
    testPage.clickButton("Apply now");
    // How this works
    testPage.clickContinue();
    var firstFormFlowSessionId = getCurrentSessionCookie();
    // Language preference
    testPage.clickContinue();
    var secondFormFlowSessionId = getCurrentSessionCookie();
    // Getting to know you
    assertThat(firstFormFlowSessionId).isNotNull();
    assertThat(firstFormFlowSessionId).isEqualTo(secondFormFlowSessionId);
    testPage.clickContinue();
    assertThat(testPage.getTitle()).isEqualTo("Personal Info");

    testPage.goBack();
    deleteSessionCookie();
//    var missingSessionCookie = getCurrentSessionCookie();
//    assertThat(missingSessionCookie).isNull();
    testPage.clickContinue();
    // Personal info
    assertThat(testPage.getTitle()).isNotEqualTo("Personal Info");
  }

  protected String getCurrentSessionCookie() {
    if (driver.manage().getCookieNamed("SESSION") == null){
      return null;
    }
    return driver.manage().getCookieNamed("SESSION").getValue();
  }

  protected void deleteSessionCookie(){
    System.out.println("SessionId before deletion: " + getCurrentSessionCookie());
    driver.manage().deleteCookieNamed("SESSION");
    System.out.println("SessionId after deletion: " + getCurrentSessionCookie());
    Set<Cookie> cookies = driver.manage().getCookies();
    cookies.forEach(cookie -> System.out.println(cookie.getName() + ": " + cookie.getValue()));
  }
}
