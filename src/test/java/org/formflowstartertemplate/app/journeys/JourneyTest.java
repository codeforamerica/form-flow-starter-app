package org.formflowstartertemplate.app.journeys;

import static org.awaitility.Awaitility.await;

import org.formflowstartertemplate.app.utils.AbstractBasePageTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public abstract class JourneyTest extends AbstractBasePageTest {

//  protected String submissionId;
//
//  @MockBean
//  protected Clock clock;
//
//  @Override
//  @BeforeEach
//  protected void setUp() throws IOException {
//    super.setUp();
//    driver.navigate().to(baseUrl);
//    when(clock.instant()).thenReturn(Instant.now());
//    when(clock.getZone()).thenReturn(ZoneOffset.UTC);
//  }

  protected void waitForErrorMessage() {
    WebElement errorMessage = driver.findElement(By.className("text--error"));
    await().until(() -> !errorMessage.getText().isEmpty());
  }
}
