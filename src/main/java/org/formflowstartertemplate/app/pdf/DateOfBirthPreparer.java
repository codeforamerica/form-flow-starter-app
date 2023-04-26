package org.formflowstartertemplate.app.pdf;

import formflow.library.data.Submission;
import formflow.library.pdf.SingleField;
import formflow.library.pdf.SubmissionField;
import formflow.library.pdf.SubmissionFieldPreparer;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DateOfBirthPreparer implements SubmissionFieldPreparer {

  @Override
  public List<SubmissionField> prepareSubmissionFields(Submission submission) {
    boolean hasState = submission.getInputData().containsKey("residentialAddressState");
    if (hasState) {
      if (submission.getInputData().get("residentialAddressState").toString().equalsIgnoreCase("MI")) {
        return List.of(new SingleField("birthDate", "XX/XX/XXXX", null));
      }
    }
    return List.of();
  }
}
