package org.ilgcc.app.pdf;

import formflow.library.data.Submission;
import formflow.library.pdf.PdfMap;
import formflow.library.pdf.SingleField;
import formflow.library.pdf.SubmissionField;
import formflow.library.pdf.SubmissionFieldPreparer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ResidentialAddressPreparer implements SubmissionFieldPreparer {

  @Override
  public Map<String, SubmissionField> prepareSubmissionFields(Submission submission, PdfMap pdfMap) {
    Map<String, SubmissionField> residentialAddressFieldMap = new HashMap<>();

    boolean applicantSelectedAnAddress = submission.getInputData().containsKey("useSuggestedResidentialAddress");
    boolean useSuggestedAddress = Boolean.parseBoolean(
        submission.getInputData().get("useSuggestedResidentialAddress").toString());
    
    List<String> addressInputs = List.of("residentialAddressStreetAddress1", "residentialAddressStreetAddress2",
        "residentialAddressCity", "residentialAddressState", "residentialAddressZipCode");

    if (applicantSelectedAnAddress) {
      if (useSuggestedAddress) {
        addressInputs.forEach(part -> {
          if (submission.getInputData().containsKey(part + "_validated")) {
            residentialAddressFieldMap.put(part, new SingleField(part,
                submission.getInputData().get(part + "_validated").toString(), null));
          }
        });
      } else {
        addressInputs.forEach(input -> {
          if (submission.getInputData().containsKey(input)) {
            residentialAddressFieldMap.put(input, new SingleField(input,
                submission.getInputData().get(input).toString(), null));
          }
        });
      }
    }

    return residentialAddressFieldMap;
  }
}