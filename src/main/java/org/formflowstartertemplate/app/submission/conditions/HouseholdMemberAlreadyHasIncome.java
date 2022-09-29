package org.formflowstartertemplate.app.submission.conditions;

import formflow.library.config.submission.Condition;
import formflow.library.data.Submission;
import java.util.ArrayList;
import java.util.Map;

public class HouseholdMemberAlreadyHasIncome implements Condition  {

    @Override
    public Boolean run(Submission submission, String data) {
        if (submission.getInputData().containsKey("income")) {
            var incomeArr = (ArrayList<Map<String, Object>>) submission.getInputData().get("income");
            var memberItationOptional = incomeArr.stream().filter(entry ->
                entry.get("householdMember").equals(data)).findFirst();
            if (memberItationOptional.isPresent()) {
                return (Boolean) memberItationOptional.get().get("isSubflowComplete");
            }
        }
        return false;
    }
}
