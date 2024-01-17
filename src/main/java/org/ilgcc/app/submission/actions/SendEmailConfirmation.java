package org.ilgcc.app.submission.actions;


import com.mailgun.model.message.MessageResponse;
import formflow.library.config.submission.Action;
import formflow.library.data.Submission;
import formflow.library.email.MailgunEmailClient;
import formflow.library.pdf.PdfService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

  public void run(Submission submission) {
    ArrayList<String> howToContactYou = (ArrayList<String>) submission.getInputData().get("howToContactYou[]");
    Boolean agreesToEmailContact = howToContactYou.stream().anyMatch(contactType -> contactType.equals("email"));
    if (!agreesToEmailContact) {
      return;
    }
    String recipientEmail = (String) submission.getInputData().get("email");
    if (recipientEmail == null || recipientEmail.isBlank()) {
      return;
    }
    Optional<Boolean> requireTls = Optional.empty();

    String emailSubject = messageSource.getMessage("email.subject", null, null);
    Object[] args = new Object[]{submission.getId().toString()};
    String emailBody = messageSource.getMessage("email.body", args, null);
    List<File> pdfs = new ArrayList<File>();
    generateApplicationAndAttachToEmail(recipientEmail, emailSubject, emailToCc, emailToBcc, emailBody, pdfs, requireTls, submission);

    String nextStepsSubject = messageSource.getMessage("next-steps-email.subject", null, null);
    String nextStepsBody = messageSource.getMessage("next-steps-email.body", null, null);
    pdfs = new ArrayList<File>();
    MessageResponse nextStepsResponse = mailgunEmailClient.sendEmail(
        nextStepsSubject,
        recipientEmail,
        Collections.emptyList(),
        Collections.emptyList(),
        nextStepsBody,
        pdfs
    );
    Boolean confirmationWasQueued = nextStepsResponse.getMessage().contains("Queued. Thank you.");
    log.info("Mailgun MessageResponse confirms message was queued (true/false): " + confirmationWasQueued);
    submission.getInputData().put("confirmationEmailQueued", confirmationWasQueued.toString());
    submission.setInputData(submission.getInputData());
  }

  public MessageResponse generateApplicationAndAttachToEmail(
      String recipientEmail,
      String emailSubject,
      List<String> emailToCc,
      List<String> emailToBcc,
      String emailBody,
      List<File>pdfs,
      Optional requireTls,
      Submission submission){
    try {
      String generateStringPrefixName = pdfService.generatePdfName(submission);
      File pdf = File.createTempFile(generateStringPrefixName, ".pdf");
      byte[] pdfByteArray = pdfService.getFilledOutPDF(submission);
      FileOutputStream fos = new FileOutputStream(pdf);
      fos.write(pdfByteArray);
      fos.flush();
      pdfs.add(pdf);
      MessageResponse response;
      if(requireTls.isPresent() && requireTls.get().equals(false)){
        mailgunEmailClient.setRequireTls(false);
      }
      response = mailgunEmailClient.sendEmail(
          emailSubject,
          recipientEmail,
          emailToCc,
          emailToBcc,
          emailBody,
          pdfs
      );
      pdf.delete();
      return response;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
