package org.formflowstartertemplate.app.submission.actions;

import formflow.library.config.submission.Action;
import formflow.library.data.FormSubmission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UpdatePersonalInfoDates implements Action {

  public void run(FormSubmission formSubmission) {
    List<String> datePrefixes = Arrays.asList("birth", "movedToUSA");
    datePrefixes.forEach(prefix -> {
      List<String> dateComponents = new ArrayList<>(3);
      if (formSubmission.formData.containsKey(prefix + "Month") && formSubmission.formData.get(prefix + "Month") != "") {
        dateComponents.add((String) formSubmission.formData.get(prefix + "Month"));
        dateComponents.add((String) formSubmission.formData.get(prefix + "Day"));
        dateComponents.add((String) formSubmission.formData.get(prefix + "Year"));
        formSubmission.formData.put(prefix + "Date", String.join("/", dateComponents));
      }
    });
  }
}