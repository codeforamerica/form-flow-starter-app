package org.formflowstartertemplate.app.submission.actions;

import formflow.library.config.submission.Action;
import formflow.library.data.FormSubmission;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@Slf4j
public class ValidateMovedToUSADate implements Action {

  private final String INPUT_NAME = "movedToUSADate";

  public Map<String, List<String>> runValidation(FormSubmission formSubmission) {
    log.info("Running ValidateMovedToUSADate!");
    Map<String, List<String>> errorMessages = new HashMap<>();
    Map<String, Object> inputData = formSubmission.getFormData();
    String movedToUSA = (String) inputData.get("movedToUSA");
    String movedToUSADate = (String) inputData.get("movedToUSADate");

    if (movedToUSA == null || !movedToUSA.equalsIgnoreCase("Yes")) {
      return errorMessages;
    }

    if (!this.isDateValid(movedToUSADate)) {
      errorMessages.put(INPUT_NAME, List.of("Please check the date entered. It is not a valid date"));
    }

    return errorMessages;
  }

  private boolean isDateValid(String date) {
    try {
      DateTimeFormatter dtf = DateTimeFormat.forPattern("M/d/yyyy");

      dtf.parseDateTime(date);
    } catch (Exception e) {
      return false;
    }
    return true;
  }
}
