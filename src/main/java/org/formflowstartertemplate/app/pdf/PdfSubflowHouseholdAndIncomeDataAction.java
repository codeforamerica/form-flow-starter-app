package org.formflowstartertemplate.app.pdf;

import formflow.library.config.submission.Action;
import formflow.library.data.Submission;
import formflow.library.pdf.PdfMapSubflow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class PdfSubflowHouseholdAndIncomeDataAction implements Action {

  /**
   * This will walk through the household members and merge their data with the income data that's available for them.  This will
   * not include the applicant's data, as that's put into a different part of the PDF file.
   *
   * @param submission The submission data we are working with
   * @return
   */
  public List<Map<String, Object>> runSubflowAction(Submission submission, PdfMapSubflow subflowMap) {
    List<Map<String, Object>> mergedData = new ArrayList<>();

    if (!submission.getInputData().containsKey("household")) {
      return mergedData;
    }

    List<Map<String, Object>> householdMembers = (List<Map<String, Object>>) (submission.getInputData().get("household"));
    List<Map<String, Object>> income = (List<Map<String, Object>>) (submission.getInputData().get("income"));

    householdMembers.stream()
        .filter(member -> (boolean) member.get("iterationIsComplete") == true)
        .forEach(member -> {
          Map<String, Object> memberInfo = new HashMap<>();
          boolean complete = (boolean) member.get("iterationIsComplete");
          if (!complete) {
            return;
          }
          memberInfo.putAll(member);

          if (income != null) {
            String fullName = member.get("householdMemberFirstName") + " " + member.get("householdMemberLastName");
            List<Map<String, Object>> memberIncomeList = income.stream()
                .filter(iteration -> iteration.get("householdMember").equals(fullName)).toList();
            if (memberIncomeList.size() > 0) {
              memberInfo.putAll(memberIncomeList.get(0));
            }
          }

          mergedData.add(memberInfo);
        });

    return mergedData;
  }

}
