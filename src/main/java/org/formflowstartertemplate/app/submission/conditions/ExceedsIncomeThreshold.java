package org.formflowstartertemplate.app.submission.conditions;

import formflow.library.config.submission.Condition;
import formflow.library.data.Submission;
import org.formflowstartertemplate.app.utils.SubmissionUtilities;

/**
 * This condition will check to see if the income the user has entered exceeds the maximum income allowable, based on their family
 * size.
 */
public class ExceedsIncomeThreshold implements Condition {

  public Boolean run(Submission submission) {
    double threshold = SubmissionUtilities.getIncomeThresholdByFamilySizeValue(submission);
    double totalIncome = SubmissionUtilities.getHouseholdTotalIncomeValue(submission);

    return totalIncome > threshold;
  }

}
