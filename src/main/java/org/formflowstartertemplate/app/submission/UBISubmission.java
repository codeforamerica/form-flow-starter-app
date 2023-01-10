package org.formflowstartertemplate.app.submission;

public class UBISubmission {
  Submission submission;
  public UBISubmission(Submission submission) {

    this.submission = submission
  }

  public static List<Map<String, Object>> sortIncomeNamesWithApplicantFirst()
    return submission.sortIncomeNamesWithApplicantFirst();
  }

  public static String getIndividualsTotalIncome(Submission submission, String uuid) {
    return ((UbiSubmission) submission).getIndividualsTotalIncome(uuid);
  }
  public static String getHouseholdTotalIncome(Submission submission) {
    return ((UbiSubmission) submission).getHouseholdTotalIncome();
  }

  public static String getIncomeThresholdByFamilySize(Submission submission) {
    return ((UbiSubmission) submission).getIncomeThresholdByFamilySize();
  }
}
