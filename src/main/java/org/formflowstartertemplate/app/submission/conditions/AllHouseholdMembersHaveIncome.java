package org.formflowstartertemplate.app.submission.conditions;

import formflow.library.config.submission.Condition;
import formflow.library.data.Submission;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class AllHouseholdMembersHaveIncome implements Condition {

  @Override
  public Boolean run(Submission submission) {
    if (submission.getInputData().containsKey("household") && submission.getInputData()
        .containsKey("income")) {
      var householdArr = (ArrayList<Map<String, Object>>) submission.getInputData()
          .get("household");
      var incomeArr = (ArrayList<Map<String, Object>>) submission.getInputData().get("income");

      // household members + applicant
      return (householdArr.size()) == incomeArr.size();
    } else
        return !submission.getInputData().containsKey("household") && submission.getInputData().containsKey("income");
  }
}
