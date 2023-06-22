package org.formflowstartertemplate.app.submission.conditions;

import formflow.library.config.submission.Condition;
import formflow.library.data.Submission;
import formflow.library.inputs.UnvalidatedField;
import org.springframework.stereotype.Component;

@Component
public class SmartySuggestionFound implements Condition {

  @Override
  public Boolean run(Submission submission) {
    return submission.getInputData().get(UnvalidatedField.VALIDATE_ADDRESS + "residentialAddress").equals("true") &&
        submission.getInputData().containsKey("residentialAddressStreetAddress1" + UnvalidatedField.VALIDATED);
  }
}
