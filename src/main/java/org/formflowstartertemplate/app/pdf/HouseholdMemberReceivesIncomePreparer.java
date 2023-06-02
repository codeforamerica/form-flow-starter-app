package org.formflowstartertemplate.app.pdf;

import formflow.library.data.Submission;
import formflow.library.pdf.PdfMap;
import formflow.library.pdf.SingleField;
import formflow.library.pdf.SubmissionField;
import formflow.library.pdf.SubmissionFieldPreparer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class HouseholdMemberReceivesIncomePreparer implements SubmissionFieldPreparer {

  @Override
  public Map<String, SubmissionField> prepareSubmissionFields(Submission submission, Map<String, Object> data, PdfMap pdfMap) {
    Map<String, SubmissionField> householdMembersReceivingIncomeMap = new HashMap<>();
    int totalIterations = pdfMap.getSubflowInfo().get("householdAndIncome").getTotalIterations();
    
    boolean hasHousehold = submission.getInputData().containsKey("hasHousehold") &&
        submission.getInputData().get("hasHousehold").equals("true");
    boolean householdHasIncome = submission.getInputData().containsKey("income");
    
    
    if (hasHousehold && householdHasIncome) {
      ArrayList<Map<String, Object>> incomeSubflow = (ArrayList<Map<String, Object>>) submission.getInputData().get("income");
      ArrayList<Map<String, Object>> houseHoldSubflow = (ArrayList<Map<String, Object>>) submission.getInputData().get("household");
      
      int maxIterations = Math.min(houseHoldSubflow.size(), totalIterations);
      
      for (int i = 0; i < maxIterations; i++) {
        String householdMemberFullName = houseHoldSubflow.get(i).get("householdMemberFirstName").toString() 
            + " " + houseHoldSubflow.get(i).get("householdMemberLastName").toString();
        if (incomeSubflow.stream().anyMatch(iteration -> iteration.get("householdMember").equals(householdMemberFullName))) {
          householdMembersReceivingIncomeMap.put(
              "householdMemberReceivesIncome_" + i,
              new SingleField("householdMemberReceivesIncome_" + i, "Yes", null));
        } else {
          householdMembersReceivingIncomeMap.put(
              "householdMemberReceivesIncome_" + i,
              new SingleField("householdMemberReceivesIncome_" + i, "No", null));
        }
      }

    }
    
   return householdMembersReceivingIncomeMap;
  }
}