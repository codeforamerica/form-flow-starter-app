package org.ilgcc.app.submission.conditions;

import formflow.library.config.submission.Condition;
import formflow.library.data.Submission;
import org.springframework.stereotype.Component;

@Component
public class HasHousehold implements Condition {

  public Boolean run(Submission submission) {
    var inputData = submission.getInputData();
    if (inputData.containsKey("hasHousehold")) {
      return inputData.get("hasHousehold").equals("true");
    }
    return false;
  }
}
