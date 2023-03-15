package org.formflowstartertemplate.app.submission.conditions;

import formflow.library.config.submission.Condition;
import formflow.library.data.Submission;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SkipAddressValidation implements Condition {

  @Value("${form-flow.address-validation.disabled}")
  private boolean isDisabled;

  @Override
  public Boolean run(Submission submission) {
    return isDisabled;
  }
}