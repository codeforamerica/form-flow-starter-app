package org.ilgcc.app.submission.actions;

import formflow.library.config.submission.Action;
import formflow.library.data.FormSubmission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import formflow.library.data.Submission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UpdatePersonalInfoDates implements Action {

  public void run(FormSubmission formSubmission, Submission submission) {
    List<String> datePrefixes = Arrays.asList("birth", "movedToUSA");
    datePrefixes.forEach(prefix -> {
      List<String> dateComponents = new ArrayList<>(3);
      if (formSubmission.formData.containsKey(prefix + "Month") && formSubmission.formData.get(prefix + "Month") != "") {
        // Add padding zero if needed to month and day
        formSubmission.formData.put(prefix + "Month", String.format("%02d", Integer.parseInt(formSubmission.formData.get(prefix + "Month").toString())));
        formSubmission.formData.put(prefix + "Day", String.format("%02d", Integer.parseInt(formSubmission.formData.get(prefix + "Day").toString())));

        // Combine into a single date field
        dateComponents.add((String) formSubmission.formData.get(prefix + "Month"));
        dateComponents.add((String) formSubmission.formData.get(prefix + "Day"));
        dateComponents.add((String) formSubmission.formData.get(prefix + "Year"));
        formSubmission.formData.put(prefix + "Date", String.join("/", dateComponents));
      }
    });
  }
}
