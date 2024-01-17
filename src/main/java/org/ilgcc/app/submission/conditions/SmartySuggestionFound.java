package org.ilgcc.app.submission.conditions;

import formflow.library.config.submission.Condition;
import formflow.library.data.Submission;
import formflow.library.inputs.FieldNameMarkers;
import org.springframework.stereotype.Component;

@Component
public class SmartySuggestionFound implements Condition {

  @Override
  public Boolean run(Submission submission) {
    return submission.getInputData().get(FieldNameMarkers.UNVALIDATED_FIELD_MARKER_VALIDATE_ADDRESS + "residentialAddress")
        .equals("true") && submission.getInputData()
        .containsKey("residentialAddressStreetAddress1" + FieldNameMarkers.UNVALIDATED_FIELD_MARKER_VALIDATED);
  }
}
