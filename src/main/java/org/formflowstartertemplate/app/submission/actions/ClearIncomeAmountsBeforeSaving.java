package org.formflowstartertemplate.app.submission.actions;

import formflow.library.config.submission.Action;
import formflow.library.data.Submission;

import java.util.ArrayList;
import java.util.Map;

/**
 * This is an example of how one could create an Action class.
 * It is referenced from the flows-config.yaml, but is specific to the UBI flow.
 */
public class ClearIncomeAmountsBeforeSaving implements Action {

    public void run(Submission submission, String uuid) {
        //grab the current household members incometypes
        var entryByUuid = Submission.getSubflowEntryByUuid("income", uuid, submission);
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
