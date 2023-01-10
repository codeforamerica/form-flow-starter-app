package org.formflowstartertemplate.app.utils;

import formflow.library.data.Submission;
import java.util.List;
import java.util.Map;
import org.formflowstartertemplate.app.data.UbiSubmission;

public class ViewUtilities {

  public static List<Map<String, Object>> sortIncomeNamesWithApplicantFirst(Submission submission) {
    return ((UbiSubmission) submission).sortIncomeNamesWithApplicantFirst();
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
