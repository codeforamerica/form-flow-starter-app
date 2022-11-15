package org.formflowstartertemplate.app.inputs;

import formflow.library.data.validators.CheckFileType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DocUpload {
  @CheckFileType
  private MultipartFile files;
}
