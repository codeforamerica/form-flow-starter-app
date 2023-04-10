package org.formflowstartertemplate.app.framework;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import formflow.library.ScreenController;
import formflow.library.data.Submission;
import formflow.library.data.SubmissionRepositoryService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.formflowstartertemplate.app.utils.AbstractMockMvcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest(properties = {"form-flow.path=flows-configs/test-cross-validation-action.yaml"})
public class CrossValidationTest extends AbstractMockMvcTest {

  Submission submission;

  private MockMvc mockMvc;

  @MockBean
  private SubmissionRepositoryService submissionRepositoryService;

  @Autowired
  private ScreenController screenController;

  private final String NO_EMAIL_ERROR_MESSAGE = "You indicated you would like to be contacted by email. Please make sure to provide an email address.";
  private final String INVALID_EMAIL_ERROR_MESSAGE = "Please enter a valid email address.";
  private String NO_PHONE_ERROR_MESSAGE = "You indicated you would like to be contacted by phone. Please make sure to provide a phone number.";

  @BeforeEach
  public void setUp() throws Exception {
    UUID submissionUUID = UUID.randomUUID();
    mockMvc = MockMvcBuilders.standaloneSetup(screenController).build();
    submission = Submission.builder().id(submissionUUID).inputData(new HashMap<>()).build();

    super.setUp();
    when(submissionRepositoryService.findOrCreate(any())).thenReturn(submission);
    when(submissionRepositoryService.findById(any())).thenReturn(Optional.of(submission));
  }

  @Test
  void shouldAcceptEmailWithPreference() throws Exception {
    postExpectingSuccess("contactInfoPreference",
        Map.of(
            "email", List.of("foo@bar.com"),
            "howToContactYou[]", List.of("email")));
  }

  @Test
  void shouldAlsoDisplayFieldValidationMessages() throws Exception {
    postExpectingFailure("contactInfoPreference",
        Map.of("email", List.of("malformed.com"), "howToContactYou[]", List.of("email")));
    assertPageHasInputError("contactInfoPreference", "email", INVALID_EMAIL_ERROR_MESSAGE);
  }

  @Test
  void shouldAcceptPhoneNumberWithPreference() throws Exception {
    postExpectingSuccess("contactInfoPreference",
        Map.of(
            "phoneNumber", List.of("223-456-7891"),
            "howToContactYou", List.of("phone")));
  }

  @Test
  void shouldFailWithPhoneNumberPreferenceNoPhone() throws Exception {
    postExpectingFailure("contactInfoPreference",
        Map.of(
            "howToContactYou[]", List.of("", "phone"),
            "phoneNumber", List.of("")));

    assertPageHasInputError("contactInfoPreference", "phoneNumber", NO_PHONE_ERROR_MESSAGE);
  }

  @Test
  void shouldFailWithEmailPreferenceNoEmail() throws Exception {
    postExpectingFailure("contactInfoPreference",
        Map.of(
            "howToContactYou[]", List.of("", "email"),
            "email", List.of("")));

    assertPageHasInputError("contactInfoPreference", "email", NO_EMAIL_ERROR_MESSAGE);
  }

  @Test
  void shouldDisplayErrorMessagesForBothPhoneAndEmailIfBothAreMissing() throws Exception {
    postExpectingFailure("contactInfoPreference",
        Map.of(
            "howToContactYou[]", List.of("", "email", "phone"),
            "email", List.of(""),
            "phoneNumber", List.of("")));

    assertPageHasInputError("contactInfoPreference", "email", NO_EMAIL_ERROR_MESSAGE);
    assertPageHasInputError("contactInfoPreference", "phoneNumber", NO_PHONE_ERROR_MESSAGE);
  }
}
