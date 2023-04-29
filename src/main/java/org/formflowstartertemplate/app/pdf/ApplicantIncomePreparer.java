package org.formflowstartertemplate.app.pdf;

import formflow.library.data.Submission;
import formflow.library.pdf.SingleField;
import formflow.library.pdf.SubmissionField;
import formflow.library.pdf.SubmissionFieldPreparer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ApplicantIncomePreparer implements SubmissionFieldPreparer {

  @Override
  public Map<String, SubmissionField> prepareSubmissionFields(Submission submission) {
    Map<String, SubmissionField> applicantIncomeFieldMap = new HashMap<>();
    
    boolean houseHoldhasIncome = submission.getInputData().containsKey("income");
    
    if (houseHoldhasIncome) {
      ArrayList<Map<String, Object>> householdIncomeSubflow = (ArrayList<Map<String, Object>>) submission.getInputData().get("income");
      
      boolean applicantHasIncome = householdIncomeSubflow.stream().anyMatch(iteration -> iteration.get("householdMember").equals("applicant"));
      
      if (applicantHasIncome) {
        applicantIncomeFieldMap.put("applicantReceivesIncome", new SingleField("applicantReceivesIncome", "Yes", null));

        Map<String, Object> applicantSubflowIteration = householdIncomeSubflow.stream()
            .filter(iteration -> iteration.get("householdMember").equals("applicant")).findFirst().orElse(null);
        
        applicantSubflowIteration.get("incomeTypes[]").forEach(incomeType -> {
          applicantIncomeFieldMap.put("applicantIncomeType" + incomeType, new SingleField("applicantIncomeType" + incomeType, "Yes", null));
        });
      } else {
        applicantIncomeFieldMap.put("applicantReceivesIncome", new SingleField("applicantReceivesIncome", "No", null));
      }
    }
  }
}
