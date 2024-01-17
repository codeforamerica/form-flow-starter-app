package org.ilgcc.app.submission.conditions;

import formflow.library.config.submission.Condition;
import formflow.library.data.Submission;
import org.ilgcc.app.utils.SubmissionUtilities;
import org.springframework.stereotype.Component;

/**
 * This condition will check to see if the income the user has entered exceeds the maximum income allowable, based on their family
 * size.
 */
@Component
public class ExceedsIncomeThreshold implements Condition {

  public Boolean run(Submission submission) {
    double threshold = SubmissionUtilities.getIncomeThresholdByFamilySizeValue(submission);
    double totalIncome = SubmissionUtilities.getHouseholdTotalIncomeValue(submission);

    return totalIncome > threshold;
  }

}
