package org.formflowstartertemplate.app.utils.regex;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.regex.Pattern;
import org.formflowstartertemplate.app.utils.RegexUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EmailRegexTest {

  @ValueSource(strings = {
      "formflow@gmail.com",
      "fce343@whis.edu",
  })
  @ParameterizedTest
  void shouldReturnTrueIfEmailIsValid(String email) {
    String test = RegexUtils.EMAIL_REGEX;
    assertThat(Pattern.matches(test, email)).isTrue();
  }
  @ValueSource(strings = {
      "test..book@gmail.com",
      ".hello@yahoo.org",
      "wha?tsssss23@.reatd?.com",
      "@book.com",
      "wakeup.com",
  })
  @ParameterizedTest
  void shouldReturnFalseIfEmailIsNotValid(String email) {
    String emailRegex = RegexUtils.EMAIL_REGEX;
    assertThat(Pattern.matches(emailRegex, email)).isFalse();
  }
}