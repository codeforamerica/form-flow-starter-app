package org.formflowstartertemplate.app.submission.actions;

import formflow.library.config.submission.Action;
import formflow.library.data.FormSubmission;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

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

    if (inputData.containsKey("movedToUSA")) {
      boolean movedToUSA = inputData.get("movedToUSA").equals("Yes");
      if (movedToUSA) {
        String movedToUSADay = (String) inputData.get(MOVED_TO_USA_DAY);
        String movedToUSAMonth = (String) inputData.get(MOVED_TO_USA_MONTH);
        String movedToUSAYear = (String) inputData.get(MOVED_TO_USA_YEAR);
        log.info("Moved to USA");
        // make sure the date entered is valid
        if (movedToUSADay == null ||
            movedToUSAMonth == null ||
            movedToUSAYear == null) {
          log.warn("Some date elements for movedToUSA not set");

          // TODO -- make actual message in messages.properties file.
          errorMessages.put(INPUT_NAME, List.of("Please checked moved to date. It's missing pieces"));
        }

        // now check date integrity.
        // Date date =
      }
    }

    return errorMessages;
  }
}
