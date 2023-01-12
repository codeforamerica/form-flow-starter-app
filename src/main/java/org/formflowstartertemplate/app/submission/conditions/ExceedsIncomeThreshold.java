package org.formflowstartertemplate.app.submission.conditions;

import formflow.library.config.submission.Condition;
import formflow.library.data.Submission;
import org.formflowstartertemplate.app.utils.SubmissionUtilities;

public class ExceedsIncomeThreshold implements Condition {

  /**
   * This is an example of how one could create a Condition class. It is referenced from the flows-config.yaml, but is specific to
   * the UBI flow.
   */
  public Boolean run(Submission submission) {
    var inputData = submission.getInputData();
    double threshold = SubmissionUtilities.getIncomeThresholdByFamilySizeValue(submission);
    double totalIncome = SubmissionUtilities.getHouseholdTotalIncomeValue(submission);

    return totalIncome > threshold;
  }

}
