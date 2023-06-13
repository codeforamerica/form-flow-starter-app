package org.formflowstartertemplate.app.pdf;

import formflow.library.data.Submission;
import formflow.library.pdf.CheckboxField;
import formflow.library.pdf.PdfMap;
import formflow.library.pdf.SubmissionField;
import formflow.library.pdf.SubmissionFieldPreparer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class LivingInHouseholdPreparer implements SubmissionFieldPreparer {

  @Override
  public Map<String, SubmissionField> prepareSubmissionFields(Submission submission, PdfMap pdfMap) {
    Map<String, SubmissionField> householdFieldMap = new HashMap<>();

    boolean doesNotLiveAlone = submission.getInputData().containsKey("hasHousehold") &&
        submission.getInputData().get("hasHousehold").equals("true");

    if (doesNotLiveAlone) {
      ArrayList<Map<String, Object>> household = (ArrayList<Map<String, Object>>) submission.getInputData().get("household");
      List<String> relationships = new ArrayList<>();
      household.forEach(iteration -> {
        if (iteration.containsKey("householdMemberRelationship")) {
          relationships.add(iteration.get("householdMemberRelationship").toString());
        }
      });

      householdFieldMap.put("householdMemberRelationship", new CheckboxField("householdMemberRelationship",
          relationships, null));
    }

    return householdFieldMap;
  }
}