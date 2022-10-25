package org.formflowstartertemplate.app.utils;

import formflow.library.data.Submission;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ViewUtilities {

  public static List<Map<String, Object>> sortIncomeNamesWithApplicantFirst(Submission submission) {
    Map<String, Object> inputData = submission.getInputData();
    if (inputData.containsKey("income")) {
      ArrayList<Map<String, Object>> subflow = (ArrayList<Map<String, Object>>) submission.getInputData()
          .get("income");
      Stream<Map<String, Object>> applicantEntry = subflow.stream()
          .filter(entry -> entry.get("householdMember").equals("applicant"));
      Stream<Map<String, Object>> nonApplicantEntries = subflow.stream()
          .filter(entry -> !entry.get("householdMember").equals("applicant"));
      return Stream.concat(applicantEntry, nonApplicantEntries).toList();
    }

    return null;
  }

  public static String getIndividualsTotalIncome(Submission submission, String uuid) {
    DecimalFormat df = new DecimalFormat("0.00");

    if (submission.getInputData().containsKey("income")) {
      ArrayList<Map<String, Object>> incomeSubflow = (ArrayList<Map<String, Object>>) submission.getInputData()
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

  public static String getHouseholdTotalIncome(Submission submission) {
    DecimalFormat df = new DecimalFormat("0.00");

    if (submission.getInputData().containsKey("income")) {
      ArrayList<Map<String, Object>> incomeSubflow = (ArrayList<Map<String, Object>>) submission.getInputData()
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

  public static int getUuid() {
    return Math.abs(new SecureRandom().nextInt());
  }
}
