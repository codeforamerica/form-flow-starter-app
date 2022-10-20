package org.formflowstartertemplate.app.submission.conditions;

import formflow.library.config.submission.Condition;
import formflow.library.data.Submission;
import java.util.ArrayList;
import java.util.Map;

public class AllHouseholdMembersHaveIncome implements Condition {

  @Override
  public Boolean run(Submission submission) {
    if (submission.getInputData().containsKey("household") && submission.getInputData()
        .containsKey("income")) {
      var householdArr = (ArrayList<Map<String, Object>>) submission.getInputData()
          .get("household");
      var incomeArr = (ArrayList<Map<String, Object>>) submission.getInputData().get("income");

      // household members + applicant
      return (householdArr.size() + 1) == incomeArr.size();
    } else if (!submission.getInputData().containsKey("household") && submission.getInputData().containsKey("income")) {
      return true;
    }
    return false;
  }
}
