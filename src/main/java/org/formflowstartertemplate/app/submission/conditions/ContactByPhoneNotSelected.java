package org.formflowstartertemplate.app.submission.conditions;

import formflow.library.config.submission.Condition;
import formflow.library.data.Submission;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component
public class ContactByPhoneNotSelected implements Condition {
  @Override
  public Boolean run(Submission submission){
    if(submission.getInputData().containsKey("howToContactYou[]")){
      var howToContactYou = (ArrayList<String>) submission.getInputData().get("howToContactYou[]");
      return !(howToContactYou.contains("phone"));
    }
    return true;
  }
}
