package org.formflowstartertemplate.app;

import formflow.library.data.Submission;
import org.springframework.context.annotation.Bean;

public class SubmissionConfiguration {
  @Bean
  public UbiSubmission submission() {
    return ((UbiSubmission) new Submission());
  }
}
