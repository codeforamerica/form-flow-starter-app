package org.formflowstartertemplate.app.inputs;

import formflow.library.data.FlowInputs;
import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("unused")
public class DocUpload extends FlowInputs {
  MultipartFile docUpload;
  MultipartFile testDocUpload;
}
