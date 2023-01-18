package org.formflowstartertemplate.app.inputs;

import formflow.library.data.FlowInputs;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DocUpload extends FlowInputs {
  MultipartFile docUpload;
}
