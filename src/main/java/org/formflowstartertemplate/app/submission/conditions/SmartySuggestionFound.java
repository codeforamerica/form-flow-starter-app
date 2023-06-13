package org.formflowstartertemplate.app.submission.conditions;

import formflow.library.config.submission.Condition;
import formflow.library.data.Submission;
import org.springframework.stereotype.Component;

@Component
public class SmartySuggestionFound implements Condition {

  @Override
  public Boolean run(Submission submission) {
    return submission.getInputData().get("validate_residentialAddress").equals("true") &&
        submission.getInputData().containsKey("residentialAddressStreetAddress1_validated");
  }
}
