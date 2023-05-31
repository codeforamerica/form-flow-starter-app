package org.formflowstartertemplate.app.pdf;

import formflow.library.data.Submission;
import formflow.library.pdf.PdfMap;
import formflow.library.pdf.SingleField;
import formflow.library.pdf.SubmissionField;
import formflow.library.pdf.SubmissionFieldPreparer;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class HouseholdMemberReceivesIncomePreparer implements SubmissionFieldPreparer {

  @Override
  public Map<String, SubmissionField> prepareSubmissionFields(Submission submission, Map<String, Object> data, PdfMap pdfMap) {
    Map<String, SubmissionField> householdMembersReceivingIncomeMap = new HashMap<>();
    int maxIterations = pdfMap.getSubflowInfo().get("householdAndIncome").getTotalIterations();
    
    boolean householdHasIncome = submission.getInputData().containsKey("income");
    
    if (householdHasIncome) {
      for (int index = 1; index <= maxIterations; index++) {
        if (data.containsKey("incomeTypes_" + index + "[]")) {
          householdMembersReceivingIncomeMap.put(
              "householdMemberReceivesIncome_" + index,
              new SingleField("householdMemberReceivesIncome_" + index, "Yes", null));
        } else {
          householdMembersReceivingIncomeMap.put(
              "householdMemberReceivesIncome_" + index,
              new SingleField("householdMemberReceivesIncome_" + index, "No", null));
        }
      }
    }
    
   return householdMembersReceivingIncomeMap;
  }
}