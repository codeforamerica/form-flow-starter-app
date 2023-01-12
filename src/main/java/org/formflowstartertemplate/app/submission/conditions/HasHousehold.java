package org.formflowstartertemplate.app.submission.conditions;

import formflow.library.config.submission.Condition;
import formflow.library.data.Submission;

public class HasHousehold implements Condition {

  public Boolean run(Submission submission) {
    var inputData = submission.getInputData();
    if (inputData.containsKey("hasHousehold")) {
      return inputData.get("hasHousehold").equals("true");
    }
    return false;
  }
}
