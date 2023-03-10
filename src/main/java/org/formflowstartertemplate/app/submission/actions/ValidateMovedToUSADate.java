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

  private final String MOVED_TO_USA_DAY = "movedToUSADay";
  private final String MOVED_TO_USA_MONTH = "movedToUSAMonth";
  private final String MOVED_TO_USA_YEAR = "movedToUSAYear";
  private final String INPUT_NAME = "movedToUSADate";

  public Map<String, List<String>> runValidation(FormSubmission formSubmission) {
    log.info("Running ValidateMovedToUSADate!");
    Map<String, List<String>> errorMessages = new HashMap<>();
    Map<String, Object> inputData = formSubmission.getFormData();
    String movedToUSA = (String) inputData.get("movedToUSA");

    if (movedToUSA == null || !movedToUSA.equalsIgnoreCase("Yes")) {
      return errorMessages;
    }

    String movedToUSADay = (String) inputData.get(MOVED_TO_USA_DAY);
    String movedToUSAMonth = (String) inputData.get(MOVED_TO_USA_MONTH);
    String movedToUSAYear = (String) inputData.get(MOVED_TO_USA_YEAR);
    if (movedToUSADay == null) {
      //errorMessages.put("movedToUSADay", List.of("message here about day"));
      errorMessages.put("movedToUSADay", List.of("message here about day"));
    }
    if (movedToUSAMonth == null) {
      errorMessages.put("movedToUSAMonth", List.of("message here about month"));
    }
    if (movedToUSAYear == null) {
      errorMessages.put("movedToUSAYear", List.of("message here about year"));
    }

    if (errorMessages.isEmpty()) {
      String fullDate = String.join("/", movedToUSAMonth, movedToUSADay, movedToUSAYear);
      if (!this.isDateValid(fullDate)) {
        errorMessages.put(INPUT_NAME, List.of("Please checked moved to date. It's missing pieces"));
      }
    }
    return errorMessages;
  }

  private boolean isDateValid(String date) {
    try {
      DateTimeFormatter dtf = DateTimeFormat.forPattern("\\d/\\d/\\d\\d\\d\\d");
      dtf.parseDateTime(date);
    } catch (Exception e) {
      return false;
    }
    return true;
  }
}
