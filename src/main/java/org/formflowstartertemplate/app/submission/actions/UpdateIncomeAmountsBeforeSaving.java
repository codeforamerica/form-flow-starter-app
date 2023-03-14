package org.formflowstartertemplate.app.submission.actions;

import formflow.library.config.submission.Action;
import formflow.library.data.Submission;

import java.util.ArrayList;
import java.util.Map;

/**
 * This will update the data submitted for Income Amounts before its saved to the database. It will take the new Income Amount
 * data and reconcile it with the data stored in the database.
 */
public class UpdateIncomeAmountsBeforeSaving implements Action {

  public void run(Submission submission, String data) {
    //grab the current household members incometypes
    var entryByUuid = submission.getSubflowEntryByUuid("income", data);
    var incomeAmounts = entryByUuid.entrySet().stream()
        .filter(e -> e.getKey().matches(".*Amount$"))
        .map(Map.Entry::getKey)
        .toList();

    var incomeTypesArray = (ArrayList<String>) entryByUuid.get("incomeTypes[]");

    incomeAmounts.stream().forEach(incomeAmount -> {
      if (!incomeTypesArray.contains(incomeAmount.replace("Amount", ""))) {
        entryByUuid.remove(incomeAmount);
      }
    });
  }
}
