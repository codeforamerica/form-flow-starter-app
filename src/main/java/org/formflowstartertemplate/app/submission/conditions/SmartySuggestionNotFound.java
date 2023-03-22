package org.formflowstartertemplate.app.submission.conditions;

import formflow.library.config.submission.Condition;
import formflow.library.data.Submission;

public class SmartySuggestionNotFound implements Condition {

  @Override
  public Boolean run(Submission submission) {
    return submission.getInputData().get("_validateresidentialAddress").equals("true") &&
        !submission.getInputData().containsKey("residentialAddressStreetAddress1_validated");
  }
}
