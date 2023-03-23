package org.formflowstartertemplate.app.submission.conditions;

import formflow.library.config.submission.Condition;
import formflow.library.data.Submission;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class IncomeSelectedSelf implements Condition {

  @Override
  public Boolean run(Submission submission, String data) {
    if (submission.getInputData().containsKey("income")) {
      // Change logic to suit your needs
      var incomeArr = (ArrayList<Map<String, Object>>) submission.getInputData().get("income");
      Map<String, Object> personsIncome = incomeArr.stream()
          .filter(entry -> entry.get("uuid").equals(data))
          .toList()
          .get(0);
      return personsIncome.get("householdMember")
          .equals("applicant");
    }
    return false;
  }
}
