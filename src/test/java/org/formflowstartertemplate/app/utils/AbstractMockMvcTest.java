package org.formflowstartertemplate.app.utils;

import static org.formflowstartertemplate.app.utils.TestUtils.resetSubmission;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.sharedHttpSession;

import formflow.library.data.Submission;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = MOCK)
@AutoConfigureMockMvc
@ContextConfiguration
public abstract class AbstractMockMvcTest {

  @MockBean
  protected Clock clock;

  @MockBean
  protected Submission submission;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  protected MockMvc mockMvc;

  protected MockHttpSession session;

  @Autowired
  protected MessageSource messageSource;

  @BeforeEach
  protected void setUp() throws Exception {
    this.mockMvc = MockMvcBuilders
        .webAppContextSetup(this.webApplicationContext)
        .apply(sharedHttpSession()) // use this session across requests
        .build();
  }

  @AfterEach
  void cleanup() {
    resetSubmission();
  }

  protected void postWithQueryParam(String pageName, String queryParam, String value)
      throws Exception {
    mockMvc.perform(
            post("/pages/" + pageName).with(csrf()).queryParam(queryParam, value))
        .andExpect(redirectedUrl("/pages/" + pageName + "/navigation"));
  }

  protected ResultActions getWithQueryParam(String pageName, String queryParam, String value)
      throws Exception {
    return mockMvc.perform(get("/pages/" + pageName).queryParam(queryParam, value))
        .andExpect(status().isOk());
  }

  protected ResultActions getWithQueryParamAndExpectRedirect(String pageName, String queryParam,
      String value,
      String expectedRedirectPageName) throws Exception {
    return mockMvc.perform(get("/pages/" + pageName).queryParam(queryParam, value))
        .andExpect(redirectedUrl("/pages/" + expectedRedirectPageName));
  }

  protected void getNavigationPageWithQueryParamAndExpectRedirect(String pageName,
      String queryParam, String value,
      String expectedPageName) throws Exception {
    var request = get("/pages/" + pageName + "/navigation")
        .queryParam(queryParam, value);
    var navigationPageUrl = mockMvc.perform(request)
        .andExpect(status().is3xxRedirection())
        .andReturn()
        .getResponse()
        .getRedirectedUrl();
    String nextPage = followRedirectsForUrl(navigationPageUrl);
    assertThat(nextPage).isEqualTo("/pages/" + expectedPageName);
  }

  protected List<File> unzip(ZipInputStream zipStream) {
    List<File> fileList = new ArrayList<>();
    try {
      ZipEntry zEntry;
      Path destination = Files.createTempDirectory("");
      while ((zEntry = zipStream.getNextEntry()) != null) {
        if (!zEntry.isDirectory()) {
          File files = new File(String.valueOf(destination), zEntry.getName());
          FileOutputStream fout = new FileOutputStream(files);
          BufferedOutputStream bufout = new BufferedOutputStream(fout);
          byte[] buffer = new byte[1024];
          int read;
          while ((read = zipStream.read(buffer)) != -1) {
            bufout.write(buffer, 0, read);
          }
          zipStream.closeEntry();//This will delete zip folder after extraction
          bufout.close();
          fout.close();
          fileList.add(files);
        }
      }
      zipStream.close();//This will delete zip folder after extraction
    } catch (Exception e) {
      System.out.println("Unzipping failed");
      e.printStackTrace();
    }
    return fileList;
  }

  protected ResultActions postExpectingSuccess(String pageName) throws Exception {
    return postWithoutData(pageName)
        .andExpect(redirectedUrl(getUrlForPageName(pageName) + "/navigation"));
  }

  protected ResultActions postStartSubflowExpectingSuccess(String pageName) throws Exception {
    String postUrl = getUrlForPageName(pageName, "new");
    return postWithoutData(postUrl).andExpect(redirectedUrl(postUrl + "/navigation"));
  }

  // Post to a page with an arbitrary number of multi-value inputs
  protected ResultActions postExpectingSuccess(String pageName, Map<String, List<String>> params)
      throws Exception {
    String postUrl = getUrlForPageName(pageName);
    return postToUrlExpectingSuccess(postUrl, postUrl + "/navigation", params);
  }

  protected ResultActions postExpectingSuccess(String pageName, String id, Map<String, List<String>> params)
      throws Exception {
    String postUrl = getUrlForPageName(pageName);
    return postToUrlExpectingSuccess(postUrl, postUrl + "/navigation", params, id);
  }

  // Post to a page with a single input that only accepts a single value
  protected ResultActions postExpectingSuccess(String pageName, String inputName, String value)
      throws Exception {
    String postUrl = getUrlForPageName(pageName);
    var params = Map.of(inputName, List.of(value));
    return postToUrlExpectingSuccess(postUrl, postUrl + "/navigation", params);
  }

  // Post to a page with a single input that accepts multiple values
  protected ResultActions postExpectingSuccess(String pageName, String inputName,
      List<String> values) throws Exception {
    String postUrl = getUrlForPageName(pageName);
    return postToUrlExpectingSuccess(postUrl, postUrl + "/navigation", Map.of(inputName, values));
  }

  protected ResultActions postToUrlExpectingSuccess(String postUrl, String redirectUrl,
      Map<String, List<String>> params) throws
      Exception {
    return mockMvc.perform(
        post(postUrl)
            .with(csrf())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .params(new LinkedMultiValueMap<>(params))
    ).andExpect(redirectedUrl(redirectUrl));
  }

  protected ResultActions postToUrlExpectingSuccess(String postUrl, String redirectUrl,
      Map<String, List<String>> params, String id) throws
      Exception {
    return mockMvc.perform(
        post(postUrl + '/' + id)
            .with(csrf())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .params(new LinkedMultiValueMap<>(params))
    ).andExpect(redirectedUrl(redirectUrl));
  }

  protected void postExpectingNextPageElementText(String pageName,
      String inputName,
      String value,
      String elementId,
      String expectedText) throws Exception {
    var nextPage = postAndFollowRedirect(pageName, inputName, value);
    assertThat(nextPage.getElementTextById(elementId)).isEqualTo(expectedText);
  }

  protected void assertPageHasElementWithId(String pageName, String elementId) throws Exception {
    var page = new FormScreen(getPage(pageName));
    assertThat(page.getElementById(elementId)).isNotNull();
  }

  protected void assertPageDoesNotHaveElementWithId(String pageName, String elementId)
      throws Exception {
    var page = new FormScreen(getPage(pageName));
    assertThat(page.getElementById(elementId)).isNull();
  }

  protected void postExpectingNextPageTitle(String pageName,
      String inputName,
      String value,
      String nextPageTitle) throws Exception {
    var nextPage = postAndFollowRedirect(pageName, inputName, value);
    assertThat(nextPage.getTitle()).isEqualTo(nextPageTitle);
  }

  protected void postExpectingNextPageTitle(String pageName,
      String inputName,
      List<String> values,
      String nextPageTitle) throws Exception {
    var nextPage = postAndFollowRedirect(pageName, inputName, values);
    assertThat(nextPage.getTitle()).isEqualTo(nextPageTitle);
  }

  protected void postExpectingNextPageTitle(String pageName,
      Map<String, List<String>> params,
      String nextPageTitle) throws Exception {
    var nextPage = postAndFollowRedirect(pageName, params);
    assertThat(nextPage.getTitle()).isEqualTo(nextPageTitle);
  }

  protected void continueExpectingNextPageTitle(String currentPageName, String nextPageTitle)
      throws Exception {
    var nextPage = getNextPageAsFormPage(currentPageName);
    assertThat(nextPage.getTitle()).isEqualTo(nextPageTitle);
  }

  protected void postExpectingRedirect(String pageName, String inputName,
      String value, String expectedNextPageName) throws Exception {
    postExpectingSuccess(pageName, inputName, value);
    assertNavigationRedirectsToCorrectNextPage(pageName, expectedNextPageName);
  }

  protected void postExpectingRedirect(String pageName, String inputName, List<String> values,
      String expectedNextPageName) throws Exception {
    postExpectingSuccess(pageName, inputName, values);
    assertNavigationRedirectsToCorrectNextPage(pageName, expectedNextPageName);
  }

  protected void postExpectingRedirect(String pageName, String expectedNextPageName)
      throws Exception {
    postExpectingSuccess(pageName);
    assertNavigationRedirectsToCorrectNextPage(pageName, expectedNextPageName);
  }

  protected void postExpectingRedirect(String pageName, Map<String, List<String>> params,
      String expectedNextPageName) throws Exception {
    postExpectingSuccess(pageName, params);
    assertNavigationRedirectsToCorrectNextPage(pageName, expectedNextPageName);
  }

  protected void assertNavigationRedirectsToCorrectNextPage(String pageName,
      String expectedNextPageName) throws Exception {
    String nextPage = followRedirectsForPageName(pageName);
    assertThat(nextPage).isEqualTo("/pages/" + expectedNextPageName);
  }

  protected void assertNavigationRedirectsToCorrectNextPageWithOption(String pageName,
      String option,
      String expectedNextPageName) throws Exception {
    String nextPage = followRedirectsForPageNameWithOption(pageName,
        option.equals("false") ? "1" : "0");
    assertThat(nextPage).isEqualTo("/pages/" + expectedNextPageName);
  }

  protected ResultActions postExpectingFailure(String pageName, String inputName, String value)
      throws Exception {
    String postUrl = getUrlForPageName(pageName);
    return mockMvc.perform(
        post(postUrl)
            .with(csrf())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .param(inputName, value)
    ).andExpect(redirectedUrl(postUrl));
  }

  protected ResultActions postExpectingFailure(String pageName, String inputName,
      List<String> values) throws Exception {
    return postExpectingFailure(pageName, fixInputNamesForParams(Map.of(inputName, values)));
  }

  protected ResultActions postExpectingFailure(String pageName, Map<String, List<String>> params)
      throws Exception {

    String postUrl = getUrlForPageName(pageName);
    return mockMvc.perform(
        post(postUrl)
            .with(csrf())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .params(new LinkedMultiValueMap<>(params))
    ).andExpect(redirectedUrl(postUrl));
  }

  protected ResultActions postExpectingFailures(String pageName, Map<String, String> params)
      throws Exception {

    String postUrl = getUrlForPageName(pageName);
    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
    params.forEach(multiValueMap::add);
    return mockMvc.perform(
        post(postUrl)
            .with(csrf())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .params(multiValueMap)
    ).andExpect(redirectedUrl(postUrl));
  }

  protected void postExpectingFailureAndAssertErrorDisplaysForThatInput(String pageName,
      String inputName,
      String value) throws Exception {
    postExpectingFailure(pageName, inputName, value);
    assertPageHasInputError(pageName, inputName);
  }


  protected void postExpectingFailureAndAssertErrorDisplaysForThatInput(String pageName,
      String inputName,
      String value, String errorMessage) throws Exception {
    postExpectingFailure(pageName, inputName, value);
    assertPageHasInputError(pageName, inputName, errorMessage);
  }

  protected void postExpectingFailureAndAssertErrorDisplaysForThatInput(String pageName,
      String inputName,
      List<String> value, String errorMessage) throws Exception {
    postExpectingFailure(pageName, inputName, value);
    assertPageHasInputError(pageName, inputName, errorMessage);
  }

  protected void postExpectingFailureAndAssertErrorsDisplaysForThatInput(String pageName,
      String inputName,
      String value, Integer numOfErrors) throws Exception {
    postExpectingFailure(pageName, inputName, value);
    assertInputHasErrors(pageName, inputName, numOfErrors);
  }

  protected void postExpectingFailureAndAssertErrorsDisplayForThatInput(String pageName,
      String inputName,
      String value, List<String> errorMessages) throws Exception {
    postExpectingFailure(pageName, inputName, value);
    assertInputHasErrors(pageName, inputName, errorMessages);
  }

  protected void postExpectingFailureAndAssertInputErrorMessages(String pageName,
      Map<String, String> inputParams,
      Map<String, List<String>> expectedErrorMessages) throws Exception {
    postExpectingFailures(pageName, inputParams);
    for (String inputName : inputParams.keySet()) {
      assertInputHasErrors(pageName, inputName, expectedErrorMessages.get(inputName));
    }
  }

  protected void postExpectingFailureAndAssertErrorDisplaysForThatDateInput(String pageName,
      String inputName,
      List<String> values) throws Exception {
    postExpectingFailure(pageName, inputName, values);
    assertPageHasDateInputError(pageName, inputName);
  }


  protected void postExpectingFailureAndAssertErrorDisplaysOnDifferentInput(String pageName,
      String inputName,
      String value,
      String inputNameWithError) throws Exception {
    postExpectingFailure(pageName, inputName, value);
    assertPageHasInputError(pageName, inputNameWithError);
  }

  protected void postExpectingFailureAndAssertErrorDisplaysOnDifferentInput(String pageName,
      String inputName,
      List<String> values,
      String inputNameWithError) throws Exception {
    postExpectingFailure(pageName, inputName, values);
    assertPageHasInputError(pageName, inputNameWithError);
  }

  @NotNull
  //TODO Get rid of this maybe?
  private Map<String, List<String>> fixInputNamesForParams(Map<String, List<String>> params) {
    return params.entrySet().stream()
        .collect(Collectors.toMap(e -> e.getKey() + "[]", Map.Entry::getValue));
  }

  protected ResultActions postWithoutData(String pageName) throws Exception {
    String postUrl = getUrlForPageName(pageName);
    return mockMvc.perform(
        post(postUrl)
            .with(csrf())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    );
  }

  protected String getUrlForPageName(String pageName, String subflow) {
    return "/flow/testFlow/" + pageName + "/" + subflow;

  }

  protected String getUrlForPageName(String pageName) {
    return "/flow/testFlow/" + pageName;
  }

  protected void assertPageHasInputError(String pageName, String inputName) throws Exception {
    var page = new FormScreen(getPage(pageName));
    assertTrue(page.hasInputError(inputName));
  }

  protected void assertPageHasInputError(String pageName, String inputName, String errorMessage)
      throws Exception {
    var page = new FormScreen(getPage(pageName));
    assertEquals(errorMessage, page.getInputError(inputName).text());
  }

  protected void assertInputHasErrors(String pageName, String inputName, Integer numOfErrors)
      throws Exception {
    var page = new FormScreen(getPage(pageName));
    assertEquals(numOfErrors, page.getInputErrors(inputName).size());
  }

  protected void assertInputHasErrors(String pageName, String inputName, List<String> errorMessages)
      throws Exception {
    var page = new FormScreen(getPage(pageName));
    // make sure there are errors returned
    assertFalse(page.getInputErrors(inputName).isEmpty(), "Expected errors on page, but there were none");
    // assert equal amount
    assertEquals(errorMessages.size(), page.getInputErrors(inputName).size(),
        "The page has a different number of errors than expected");
    // now check if they match the expected ones.
    assertTrue(errorMessages.containsAll(page.getInputErrors(inputName).stream().map(Element::ownText).toList()),
        "The error messages expected do match what was returned");
  }

  protected void assertPageHasDateInputError(String pageName, String inputName) throws Exception {
    var page = new FormScreen(getPage(pageName));
    assertTrue(page.hasDateInputError());
  }

  protected void assertPageDoesNotHaveInputError(String pageName, String inputName)
      throws Exception {
    var page = new FormScreen(getPage(pageName));
    assertFalse(page.hasInputError(inputName));
  }

  protected void assertPageHasWarningMessage(String pageName, String warningMessage)
      throws Exception {
    var page = new FormScreen(getPage(pageName));
    assertEquals(page.getWarningMessage(), warningMessage);
  }

  @NotNull
  protected ResultActions getPage(String pageName) throws Exception {
    return mockMvc.perform(get("/flow/testFlow/" + pageName));
  }

  @NotNull
  protected ResultActions getPageExpectingSuccess(String pageName) throws Exception {
    return getPage(pageName).andExpect(status().isOk());
  }

  /**
   * Accepts the page you are on and follows the redirects to get the next page
   *
   * @param currentPageName the page
   * @return a form page that can be asserted against
   */
  protected FormScreen getNextPageAsFormPage(String currentPageName) throws Exception {
    String nextPage = followRedirectsForPageName(currentPageName);
    return new FormScreen(mockMvc.perform(get(nextPage)));
  }

  @NotNull
  private String followRedirectsForPageName(String currentPageName) throws Exception {
    var nextPage = "/flow/testFlow/" + currentPageName + "/navigation";
    while (Objects.requireNonNull(nextPage).contains("/navigation")) {
      // follow redirects
      nextPage = mockMvc.perform(get(nextPage))
          .andExpect(status().is3xxRedirection()).andReturn()
          .getResponse()
          .getRedirectedUrl();
    }
    return nextPage;
  }

  private String followRedirectsForUrl(String currentPageUrl) throws Exception {
    var nextPage = currentPageUrl;
    while (Objects.requireNonNull(nextPage).contains("/navigation")) {
      // follow redirects
      nextPage = mockMvc.perform(get(nextPage))
          .andExpect(status().is3xxRedirection()).andReturn()
          .getResponse()
          .getRedirectedUrl();
    }
    return nextPage;
  }

  protected FormScreen postAndFollowRedirect(String pageName, String inputName, String value) throws
      Exception {
    postExpectingSuccess(pageName, inputName, value);
    return getNextPageAsFormPage(pageName);
  }

  protected FormScreen postAndFollowRedirect(String pageName, Map<String, List<String>> params)
      throws
      Exception {
    postExpectingSuccess(pageName, params);
    return getNextPageAsFormPage(pageName);
  }

  protected FormScreen postAndFollowRedirect(String pageName) throws
      Exception {
    postExpectingSuccess(pageName);
    return getNextPageAsFormPage(pageName);
  }

  protected FormScreen postAndFollowRedirect(String pageName, String inputName,
      List<String> values) throws
      Exception {
    postExpectingSuccess(pageName, inputName, values);
    return getNextPageAsFormPage(pageName);
  }

  protected void getPageAndExpectRedirect(String getPageName, String redirectPageName)
      throws Exception {
    getPage(getPageName).andExpect(redirectedUrl("/pages/" + redirectPageName));
  }

  protected FormScreen goBackTo(String getPageName)
      throws Exception {
    return new FormScreen(getPage(getPageName));
  }

  protected void assertCorrectPageTitle(String pageName, String pageTitle) throws Exception {
    assertThat(new FormScreen(getPage(pageName)).getTitle()).isEqualTo(pageTitle);
  }

  protected void clickContinueOnInfoPage(String pageName, String continueButtonText,
      String expectedNextPageName) throws Exception {
    FormScreen page = new FormScreen(getPage(pageName));
    page.assertLinkWithTextHasCorrectUrl(continueButtonText,
        "/pages/%s/navigation?option=0".formatted(pageName));
    assertNavigationRedirectsToCorrectNextPage(pageName, expectedNextPageName);
  }

  @NotNull
  private String followRedirectsForPageNameWithOption(String currentPageName, String option)
      throws Exception {
    var nextPage = "/pages/" + currentPageName + "/navigation?option=" + option;
    while (Objects.requireNonNull(nextPage).contains("/navigation")) {
      // follow redirects
      nextPage = mockMvc.perform(get(nextPage))
          .andExpect(status().is3xxRedirection()).andReturn()
          .getResponse()
          .getRedirectedUrl();
    }
    return nextPage;
  }
}
