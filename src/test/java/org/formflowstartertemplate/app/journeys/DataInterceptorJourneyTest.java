package org.formflowstartertemplate.app.journeys;

import static org.assertj.core.api.Assertions.assertThat;

import org.formflowstartertemplate.app.utils.AbstractBasePageTest;
import org.junit.jupiter.api.Test;

public class DataInterceptorJourneyTest extends AbstractBasePageTest {

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
    testPage.clickContinue();
    assertThat(testPage.getTitle()).isNotEqualTo("Personal Info");
  }

  protected String getCurrentSessionCookie() {
    if (driver.manage().getCookieNamed("SESSION") == null){
      return null;
    }
    return driver.manage().getCookieNamed("SESSION").getValue();
  }

  protected void deleteSessionCookie(){
    driver.manage().deleteCookieNamed("SESSION");
  }
}
