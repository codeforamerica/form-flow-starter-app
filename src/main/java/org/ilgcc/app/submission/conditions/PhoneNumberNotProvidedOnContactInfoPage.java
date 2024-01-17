package org.ilgcc.app.submission.conditions;

import formflow.library.config.submission.Condition;
import formflow.library.data.Submission;

import org.springframework.stereotype.Component;

@Component
public class PhoneNumberNotProvidedOnContactInfoPage implements Condition {
  @Override
  public Boolean run(Submission submission){
    if(submission.getInputData().containsKey("phoneNumber")){
      String phoneNumber = (String)submission.getInputData().get("phoneNumber");
      return phoneNumber.isBlank();
    }
    return false;
  }
}
