package org.formflowstartertemplate.app.submission.conditions;

import formflow.library.config.submission.Condition;
import formflow.library.data.Submission;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HomeAddressValidationIsOff implements Condition {

  @Value("${form-flow.address-validation.disabled:false}")
  private boolean isAddressValidationDisabled;

  @Override
  public Boolean run(Submission submission) {
    boolean addrValidationOffAtFragmentLevel = submission.getInputData().get("validate_residentialAddress").equals("false");
    return isAddressValidationDisabled || addrValidationOffAtFragmentLevel;
  }
}
