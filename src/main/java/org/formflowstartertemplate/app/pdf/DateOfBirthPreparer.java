package org.formflowstartertemplate.app.pdf;

import formflow.library.data.Submission;
import formflow.library.pdf.PdfMap;
import formflow.library.pdf.SingleField;
import formflow.library.pdf.SubmissionField;
import formflow.library.pdf.SubmissionFieldPreparer;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class DateOfBirthPreparer implements SubmissionFieldPreparer {

  @Override
  public Map<String, SubmissionField> prepareSubmissionFields(Submission submission, PdfMap pdfMap) {
    boolean hasState = submission.getInputData().containsKey("residentialAddressState");
    if (hasState) {
      if (submission.getInputData().get("residentialAddressState").toString().equalsIgnoreCase("MI")) {
        return Map.of("birthDate", new SingleField("birthDate", "XX/XX/XXXX", null));
      }
    }
    return Map.of();
  }
}
