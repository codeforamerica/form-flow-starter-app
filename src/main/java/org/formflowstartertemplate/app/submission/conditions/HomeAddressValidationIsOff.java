package org.formflowstartertemplate.app.submission.conditions;

import formflow.library.config.submission.Condition;
import formflow.library.data.Submission;
import formflow.library.inputs.UnvalidatedField;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HomeAddressValidationIsOff implements Condition {

  @Value("${form-flow.address-validation.disabled:false}")
  private boolean isAddressValidationDisabled;

  @Override
  public Boolean run(Submission submission) {
    if (isAddressValidationDisabled) {
      return true;
    }

    return submission.getInputData().get(UnvalidatedField.VALIDATE_ADDRESS + "residentialAddress").equals("false");
  }
}
