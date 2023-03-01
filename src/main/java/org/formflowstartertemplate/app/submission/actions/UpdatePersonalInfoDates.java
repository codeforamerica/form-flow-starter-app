package org.formflowstartertemplate.app.submission.actions;

import formflow.library.config.submission.Action;
import formflow.library.data.FormSubmission;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UpdatePersonalInfoDates implements Action {

  public void run(FormSubmission formSubmission) {
    List<String> dateComponents = new ArrayList<>(3);
    dateComponents.add((String) formSubmission.formData.get("dateMonth"));
    dateComponents.add((String) formSubmission.formData.get("dateDay"));
    dateComponents.add((String) formSubmission.formData.get("dateYear"));
    formSubmission.formData.put("birthDate", String.join("/", dateComponents));
  }
}
