package org.formflowstartertemplate.app.submission.conditions;

import formflow.library.config.submission.Condition;
import formflow.library.data.Submission;
import java.util.ArrayList;
import java.util.Map;

/**
 * This is an example of how one could create a Condition class.
 * It is referenced from the flows-config.yaml, but is specific to the UBI flow.
 */
public class incomeSelectedSelf implements Condition  {

    @Override
    public Boolean run(Submission submission, String uuid) {
        if (submission.getInputData().containsKey("income")) {
            // Change logic to suit your needs
            var incomeArr = (ArrayList<Map<String, Object>>) submission.getInputData().get("income");
            Map<String, Object> personsIncome = incomeArr.stream()
                .filter(entry -> entry.get("uuid").equals(uuid)).toList().get(0);
            return personsIncome.get("householdMember")
                .equals("applicant");
        }
        return false;
    }
}
