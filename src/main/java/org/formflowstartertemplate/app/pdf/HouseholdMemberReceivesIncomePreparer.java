package org.formflowstartertemplate.app.pdf;

import formflow.library.data.Submission;
import formflow.library.pdf.CheckboxField;
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
  public Map<String, SubmissionField> prepareSubmissionFields(Submission submission, PdfMap pdfMap) {
    Map<String, SubmissionField> householdMemberSubmissionFields = new HashMap<>();

    boolean hasHousehold = submission.getInputData().containsKey("hasHousehold") &&
        submission.getInputData().get("hasHousehold").equals("true");
    boolean householdHasIncome = submission.getInputData().containsKey("income");

    if (hasHousehold && householdHasIncome) {
      ArrayList<Map<String, Object>> incomeSubflow = (ArrayList<Map<String, Object>>) submission.getInputData().get("income");
      ArrayList<Map<String, Object>> houseHoldSubflow = (ArrayList<Map<String, Object>>) submission.getInputData()
          .get("household");

      int maxIterations = Math.min(houseHoldSubflow.size(), pdfMap.getSubflowInfo().get("householdAndIncome").getTotalIterations());

      for (int i = 0; i < maxIterations; i++) {
        String householdMemberFirstName = houseHoldSubflow.get(i).get("householdMemberFirstName").toString();
        String householdMemberLastName = houseHoldSubflow.get(i).get("householdMemberLastName").toString();
        String householdMemberRelationship = houseHoldSubflow.get(i).get("householdMemberRelationship").toString();
        String householdMemberRecentlyMovedToUs = houseHoldSubflow.get(i).get("householdMemberRecentlyMovedToUS").toString();
        
        int iterationNumber = i + 1;
        householdMemberSubmissionFields.putAll(Map.of(
            "householdMemberFirstName_" + iterationNumber, new SingleField("householdMemberFirstName", householdMemberFirstName, iterationNumber),
            "householdMemberLastName_" + iterationNumber, new SingleField("householdMemberLastName", householdMemberLastName, iterationNumber),
            "householdMemberRelationship_" + iterationNumber, new SingleField("householdMemberRelationship", householdMemberRelationship, iterationNumber),
            "householdMemberRecentlyMovedToUS_" + iterationNumber,  new SingleField("householdMemberRecentlyMovedToUS", householdMemberRecentlyMovedToUs, iterationNumber)
        ));

        boolean hasHouseholdMemberMovedToUSADate = houseHoldSubflow.get(i).containsKey("householdMemberMovedToUSADate");
        boolean hasHouseholdMemberMovedFromCountry = houseHoldSubflow.get(i).containsKey("householdMemberMovedFromCountry");
        
        
        if (hasHouseholdMemberMovedToUSADate) {
          String householdMemberMovedToUSADate = houseHoldSubflow.get(i).get("householdMemberMovedToUSADate").toString();
          householdMemberSubmissionFields.put("householdMemberMovedToUSADate_" + iterationNumber,
              new SingleField("householdMemberMovedToUSADate", householdMemberMovedToUSADate, iterationNumber));
        }
        
        if (hasHouseholdMemberMovedFromCountry) {
          String householdMemberMovedFromCountry = houseHoldSubflow.get(i).get("householdMemberMovedFromCountry").toString();
          householdMemberSubmissionFields.put("householdMemberMovedFromCountry_" + iterationNumber,
              new SingleField("householdMemberMovedFromCountry", householdMemberMovedFromCountry, iterationNumber));
        }

        Map<String, Object> householdMember = incomeSubflow.stream().filter(
                iteration -> iteration.get("householdMember").equals(householdMemberFirstName + " " + householdMemberLastName))
            .findFirst().orElse(null);

        if (householdMember != null) {
          householdMemberSubmissionFields.put("householdMemberReceivesIncome_" + iterationNumber,
              new SingleField("householdMemberReceivesIncome", "Yes", iterationNumber));

          ArrayList<String> incomeTypes = (ArrayList<String>) householdMember.get("incomeTypes[]");
          householdMemberSubmissionFields.put("incomeTypes_" + iterationNumber,
              new CheckboxField("incomeTypes_" + iterationNumber, incomeTypes, null));

          final int finalIterationNumber = iterationNumber;
          incomeTypes.forEach(incomeType -> {
            String incomeAmount = householdMember.get(incomeType + "Amount").toString();
            householdMemberSubmissionFields.put(incomeType + "Amount_" + finalIterationNumber,
                new SingleField(incomeType + "Amount", incomeAmount, finalIterationNumber));
          });

        } else {
          householdMemberSubmissionFields.put("householdMemberReceivesIncome_" + iterationNumber,
              new SingleField("householdMemberReceivesIncome", "No", iterationNumber));
        }
      }
    }

    return householdMemberSubmissionFields;
  }
}