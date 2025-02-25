package org.formflowstartertemplate.app.submission.conditions;

import formflow.library.config.submission.Condition;
import formflow.library.data.Submission;
import formflow.library.inputs.FieldNameMarkers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HomeAddressValidationIsOff implements Condition {

  @Value("${form-flow.address-validation.enabled:true}")
  private boolean isAddressValidationEnabled;

  @Override
  public Boolean run(Submission submission) {
    if (!isAddressValidationEnabled) {
      return true;
    }

    return submission.getInputData().get(FieldNameMarkers.UNVALIDATED_FIELD_MARKER_VALIDATE_ADDRESS + "residentialAddress")
        .equals("false");
  }
}
