package org.formflowstartertemplate.app.submission.conditions;

import formflow.library.config.submission.Condition;
import formflow.library.data.Submission;
import java.util.ArrayList;
import java.util.Map;

/**
 * This is an example of how one could create a Condition class.
 * It is referenced from the flows-config.yaml, but is specific to the UBI flow.
 */
public class householdMemberAlreadyHasIncome implements Condition  {

    @Override
    public Boolean run(Submission submission, String householdMemberName) {
        if (submission.getInputData().containsKey("income")) {
            var incomeArr = (ArrayList<Map<String, Object>>) submission.getInputData().get("income");
            var memberItationOptional = incomeArr.stream().filter(entry ->
                entry.get("householdMember").equals(householdMemberName)).findFirst();
            if (memberItationOptional.isPresent()) {
                return (Boolean) memberItationOptional.get().get("isSubflowComplete");
            }
        }
        return false;
    }
}
