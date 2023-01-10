package org.formflowstartertemplate.app.utils;

import formflow.library.data.Submission;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.formflowstartertemplate.app.UbiSubmission;

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
