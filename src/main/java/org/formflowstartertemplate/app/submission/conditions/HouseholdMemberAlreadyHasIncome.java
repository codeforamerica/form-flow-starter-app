package org.formflowstartertemplate.app.submission.conditions;

import formflow.library.config.submission.Condition;
import formflow.library.data.Submission;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class HouseholdMemberAlreadyHasIncome implements Condition {

  @Override
  public Boolean run(Submission submission, String data) {
    if (submission.getInputData().containsKey("income")) {
      var incomeArr = (ArrayList<Map<String, Object>>) submission.getInputData().get("income");
      var memberIterationOptional = incomeArr.stream()
          .filter(entry -> entry.get("householdMember").equals(data))
          .findFirst();
      if (memberIterationOptional.isPresent()) {
        return (Boolean) memberIterationOptional.get().get("iterationIsComplete");
      }
    }
    return false;
  }
}
