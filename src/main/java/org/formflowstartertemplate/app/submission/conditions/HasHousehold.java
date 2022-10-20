package org.formflowstartertemplate.app.submission.conditions;

import formflow.library.config.submission.Condition;
import formflow.library.data.Submission;

/**
 * This is an example of how one could create a Condition class. It is referenced from the flows-config.yaml, but is specific to
 * the UBI flow.
 */
public class HasHousehold implements Condition {

  public Boolean run(Submission submission) {
    var inputData = submission.getInputData();
    if (inputData.containsKey("hasHousehold")) {
      return inputData.get("hasHousehold").equals("true");
    }
    return false;
  }
}
