package org.formflowstartertemplate.app.submission.conditions;

import formflow.library.config.submission.Condition;
import formflow.library.data.Submission;
import org.springframework.stereotype.Component;

@Component
public class TestSmartySuggestionFound implements Condition {

  @Override
  public Boolean run(Submission submission) {
    return submission.getInputData().get("validate_testAddress").equals("true") &&
        submission.getInputData().containsKey("testAddressStreetAddress1_validated");
  }
}
