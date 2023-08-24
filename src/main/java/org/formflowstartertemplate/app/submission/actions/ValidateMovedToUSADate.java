package org.formflowstartertemplate.app.submission.actions;

import formflow.library.data.FormSubmission;
import formflow.library.data.Submission;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ValidateMovedToUSADate extends VerifyDate {

  private final String INPUT_NAME = "movedToUSADate";

  public Map<String, List<String>> runValidation(FormSubmission formSubmission, Submission submission) {
    log.info("Running ValidateMovedToUSADate");
    Map<String, List<String>> errorMessages = new HashMap<>();
    Map<String, Object> inputData = formSubmission.getFormData();
    String movedToUSA = (String) inputData.get("movedToUSA");
    String movedToUSADate = (String) inputData.get("movedToUSADate");

    if (movedToUSA == null || !movedToUSA.equalsIgnoreCase("Yes")) {
      return errorMessages;
    }

    if (this.isDateInvalid(movedToUSADate)) {
      errorMessages.put(INPUT_NAME, List.of("Please check the date entered. It is not a valid date."));
    }

    return errorMessages;
  }
}
