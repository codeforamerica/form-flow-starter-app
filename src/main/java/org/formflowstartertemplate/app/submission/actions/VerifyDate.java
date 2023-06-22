package org.formflowstartertemplate.app.submission.actions;

import formflow.library.config.submission.Action;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class VerifyDate implements Action {

  protected boolean isDateValid(String date) {
    try {
      DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy");

      dtf.parseDateTime(date);
    } catch (Exception e) {
      return false;
    }
    return true;
  }
}
