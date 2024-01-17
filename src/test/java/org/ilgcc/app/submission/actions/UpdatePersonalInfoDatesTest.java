package org.ilgcc.app.submission.actions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import formflow.library.data.FormSubmission;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class UpdatePersonalInfoDatesTest {

  @Test
  void shouldAddLeadingZeros() throws Exception {
    var prefix = "birth";
    Map<String, Object> hashMap = new HashMap<>();
    hashMap.put(prefix + "Month", "2");
    hashMap.put(prefix + "Day", "1");
    hashMap.put(prefix + "Year", "1990");
    FormSubmission formSubmission = new FormSubmission(hashMap);
    var updatePersonalInfoDatesAction = new UpdatePersonalInfoDates();
    updatePersonalInfoDatesAction.run(formSubmission, null);

    assertThat(formSubmission.getFormData().get(prefix + "Month")).isEqualTo("02");
    assertThat(formSubmission.getFormData().get(prefix + "Day")).isEqualTo("01");
    assertThat(formSubmission.getFormData().get(prefix + "Date")).isEqualTo("02/01/1990");
  }

  @Test
  void shouldNotAddZeroWhenNotNeeded() throws Exception {
    var prefix = "birth";
    Map<String, Object> hashMap = new HashMap<>();
    hashMap.put(prefix + "Month", "12");
    hashMap.put(prefix + "Day", "11");
    hashMap.put(prefix + "Year", "1990");
    FormSubmission formSubmission = new FormSubmission(hashMap);
    var updatePersonalInfoDatesAction = new UpdatePersonalInfoDates();
    updatePersonalInfoDatesAction.run(formSubmission, null);

    assertThat(formSubmission.getFormData().get(prefix + "Month")).isEqualTo("12");
    assertThat(formSubmission.getFormData().get(prefix + "Day")).isEqualTo("11");
    assertThat(formSubmission.getFormData().get(prefix + "Date")).isEqualTo("12/11/1990");
  }

  @Test
  void shouldNotAddZeroWhenAlreadyThere() throws Exception {
    var prefix = "birth";
    Map<String, Object> hashMap = new HashMap<>();
    hashMap.put(prefix + "Month", "05");
    hashMap.put(prefix + "Day", "03");
    hashMap.put(prefix + "Year", "1990");
    FormSubmission formSubmission = new FormSubmission(hashMap);
    var updatePersonalInfoDatesAction = new UpdatePersonalInfoDates();
    updatePersonalInfoDatesAction.run(formSubmission, null);

    assertThat(formSubmission.getFormData().get(prefix + "Month")).isEqualTo("05");
    assertThat(formSubmission.getFormData().get(prefix + "Day")).isEqualTo("03");
    assertThat(formSubmission.getFormData().get(prefix + "Date")).isEqualTo("05/03/1990");
  }
}
