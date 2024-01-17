package org.ilgcc.app.submission.actions;

import formflow.library.config.submission.Action;
import formflow.library.data.FormSubmission;
import formflow.library.data.Submission;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UpdateHouseholdMemberMovedToUSADate implements Action {

  public void run(FormSubmission formSubmission, Submission submission, String id) {
    String prefix = "householdMemberMovedToUSA";
    List<String> dateComponents = new ArrayList<>(3);
    if (formSubmission.formData.containsKey(prefix + "Month") && formSubmission.formData.get(prefix + "Month") != "") {
      dateComponents.add((String) formSubmission.formData.get(prefix + "Month"));
      dateComponents.add((String) formSubmission.formData.get(prefix + "Day"));
      dateComponents.add((String) formSubmission.formData.get(prefix + "Year"));
      formSubmission.formData.put(prefix + "Date", String.join("/", dateComponents));
    }
  }
}