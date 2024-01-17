package org.ilgcc.app.pdf;

import formflow.library.data.Submission;
import formflow.library.pdf.CheckboxField;
import formflow.library.pdf.PdfMap;
import formflow.library.pdf.SingleField;
import formflow.library.pdf.SubmissionField;
import formflow.library.pdf.SubmissionFieldPreparer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ApplicantIncomePreparer implements SubmissionFieldPreparer {

  @Override
  public Map<String, SubmissionField> prepareSubmissionFields(Submission submission, PdfMap pdfMap) {
    Map<String, SubmissionField> applicantIncomeFieldMap = new HashMap<>();
    
    boolean applicantReportedTotalHouseholdIncome = submission.getInputData().containsKey("reportedTotalAnnualHouseholdIncome");
    
    if (applicantReportedTotalHouseholdIncome) {
      if (submission.getInputData().get("reportedTotalAnnualHouseholdIncome").equals("0")) {
        applicantIncomeFieldMap.put("applicantReceivesIncome", new SingleField("applicantReceivesIncome", "No", null));
      } else {
        applicantIncomeFieldMap.put("applicantReceivesIncome", new SingleField("applicantReceivesIncome", "Yes", null));
      }
    }

    boolean householdHasIncome = submission.getInputData().containsKey("income");

    if (householdHasIncome) {
      ArrayList<Map<String, Object>> householdIncomeSubflow = (ArrayList<Map<String, Object>>) submission.getInputData()
          .get("income");

      boolean applicantEnteredIncomeAmounts = householdIncomeSubflow.stream()
          .anyMatch(iteration -> iteration.get("householdMember").equals("applicant"));

      if (applicantEnteredIncomeAmounts) {
        applicantIncomeFieldMap.put("applicantReceivesIncome", new SingleField("applicantReceivesIncome", "Yes", null));

        Map<String, Object> applicantSubflowIteration = householdIncomeSubflow.stream().filter(iteration ->
            iteration.get("householdMember").equals("applicant")).findFirst().orElse(null);
        List<String> incomeTypes = (List<String>) applicantSubflowIteration.get("incomeTypes[]");
        applicantIncomeFieldMap.put(
            "applicantIncomeTypes",
            new CheckboxField("applicantIncomeTypes", incomeTypes, null));
        incomeTypes.forEach(incomeType -> {
          String inputName = incomeType + "Amount";
          applicantIncomeFieldMap.put(
              inputName,
              new SingleField(inputName, applicantSubflowIteration.get(inputName).toString(), null));
        });

      } else {
        applicantIncomeFieldMap.put("applicantReceivesIncome", new SingleField("applicantReceivesIncome", "No", null));
      }
    }

    return applicantIncomeFieldMap;
  }
}