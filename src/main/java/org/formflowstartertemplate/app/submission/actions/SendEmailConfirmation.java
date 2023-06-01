package org.formflowstartertemplate.app.submission.actions;


import formflow.library.PdfService;
import formflow.library.config.submission.Action;
import formflow.library.data.Submission;
import formflow.library.email.EmailUtils;
import formflow.library.email.MailgunEmailClient;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class SendEmailConfirmation implements Action {
  @Autowired
  MessageSource messageSource;
  @Autowired
  private PdfService pdfService;
  @Autowired
  private MailgunEmailClient mailgunEmailClient;

  @Value("${form-flow.flow.ubi.email.confirmation.cc:}")
  private List<String> emailToCc;

  @Value("${form-flow.flow.ubi.email.confirmation.bcc:}")
  private List<String> emailToBcc;
public void run(Submission submission){
  ArrayList<String> howToContactYou = (ArrayList<String>) submission.getInputData().get("howToContactYou[]");
  Boolean agreesToEmailContact = howToContactYou.stream().anyMatch(contactType -> contactType.equals("email"));
  if(!agreesToEmailContact){
    return;
  }
  String recipientEmail = (String) submission.getInputData().get("email");
  if (recipientEmail == null || recipientEmail.isBlank()){
    return;
  }
  String emailSubject = messageSource.getMessage("email.subject", null, null);
  Object[] args = new Object[] {submission.getId().toString()};
  String emailBody = messageSource.getMessage("email.body", args, null);
  String htmlEmailBody = EmailUtils.wrapHtml(emailBody);

  Boolean requireTls = Boolean.TRUE;
  List<File> pdfs = new ArrayList<File>();
  try {
    String generateStringPrefixName = pdfService.generatePdfName(submission.getFlow(), submission.getId().toString());
    File pdf = File.createTempFile(generateStringPrefixName,".pdf");
    byte[] pdfByteArray = pdfService.getFilledOutPDF(submission.getFlow(), submission.getId().toString());
    FileOutputStream fos = new FileOutputStream(pdf);
    fos.write(pdfByteArray);
    fos.flush();
    pdfs.add(pdf);
    mailgunEmailClient.sendEmail(
        emailSubject,
        recipientEmail,
        emailToCc,
        emailToBcc,
        htmlEmailBody,
        pdfs,
        requireTls
    );
    pdf.delete();
  } catch (IOException e) {
    throw new RuntimeException(e);
  }
}
}
