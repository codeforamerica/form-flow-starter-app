package org.formflowstartertemplate.app.conditions;

import formflow.library.config.Condition;
import formflow.library.data.Submission;

/**
 * This is mostly an example class.  It is referenced from the flows-config.yaml,
 * but is specific to the UBI flow.
 */
public class HasHousehold extends Condition {

    public Boolean runCondition(Submission submission) {
        var inputData = submission.getInputData();
        if (inputData.containsKey("hasHousehold")) {
            return inputData.get("hasHousehold").equals("true");
        }
        return false;
    }
}
