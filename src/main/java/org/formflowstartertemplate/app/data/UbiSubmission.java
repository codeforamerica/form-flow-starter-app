package org.formflowstartertemplate.app.data;

import formflow.library.data.Submission;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class UbiSubmission extends Submission {

  public List<Map<String, Object>> sortIncomeNamesWithApplicantFirst() {
    Map<String, Object> inputData = getInputData();
    if (inputData.containsKey("income")) {
      ArrayList<Map<String, Object>> subflow = (ArrayList<Map<String, Object>>) getInputData()
          .get("income");
      Stream<Map<String, Object>> applicantEntry = subflow.stream()
          .filter(entry -> entry.get("householdMember").equals("applicant"));
      Stream<Map<String, Object>> nonApplicantEntries = subflow.stream()
          .filter(entry -> !entry.get("householdMember").equals("applicant"));
      return Stream.concat(applicantEntry, nonApplicantEntries).toList();
    }

    return null;
  }

  public String getIndividualsTotalIncome(String uuid) {
    DecimalFormat df = new DecimalFormat("0.00");

    if (getInputData().containsKey("income")) {
      ArrayList<Map<String, Object>> incomeSubflow = (ArrayList<Map<String, Object>>) getInputData()
          .get("income");
      Map<String, Object> individualsIncomeEntry = incomeSubflow.stream()
          .filter(entry -> entry.get("uuid").equals(uuid)).toList().get(0);
      ArrayList<String> incomeTypes = (ArrayList<String>) individualsIncomeEntry.get("incomeTypes[]");
      List<BigDecimal> incomeTypeAmounts = incomeTypes.stream()
          .map(type -> new BigDecimal((String) individualsIncomeEntry.get(type + "Amount")))
          .toList();
      return df.format(incomeTypeAmounts.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    return null;
  }

  public String getHouseholdTotalIncome() {
    DecimalFormat df = new DecimalFormat("0.00");

    if (getInputData().containsKey("income")) {
      ArrayList<Map<String, Object>> incomeSubflow = (ArrayList<Map<String, Object>>) getInputData()
          .get("income");
      ArrayList<BigDecimal> amounts = new ArrayList<>();
      incomeSubflow.forEach(iteration -> {
        iteration.forEach((key, value) -> {
          if (key.contains("Amount")) {
            amounts.add(new BigDecimal((String) value));
          }
        });
      });

      return df.format(amounts.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    return null;
  }

  public String getFamilySize() {
    //Add all household member and the applicant to get total family size
    int familySize = 1;
    if (getInputData().get("household") != null) {
      var household = (ArrayList<LinkedHashMap<String, String>>) getInputData().get("household");
      familySize = household.size() + familySize;
    }
    return (Integer.toString(familySize));
  }

  public String getIncomeThresholdByFamilySize() {
    String defaultThreshold = String.valueOf(116775 + ((Integer.parseInt(getFamilySize()) - 8) * 11800));
    return switch (Integer.parseInt(getFamilySize())) {
      case 1 -> "33,975";
      case 2 -> "45,775";
      case 3 -> "57,575";
      case 4 -> "69,375";
      case 5 -> "81,175";
      case 6 -> "92,975";
      case 7 -> "104,775";
      case 8 -> "116,775";
      default -> defaultThreshold;
    };
  }
}
