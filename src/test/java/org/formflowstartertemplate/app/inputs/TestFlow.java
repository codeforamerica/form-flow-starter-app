package org.formflowstartertemplate.app.inputs;

import formflow.library.data.FlowInputs;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.web.multipart.MultipartFile;

@TestConfiguration
@SuppressWarnings("unused")
public class TestFlow extends FlowInputs {

  @NotBlank(message = "{validations.make-sure-to-provide-a-first-name}")
  String firstName;

  String textInput;
  String areaInput;
  String dateDay;
  String dateMonth;
  String dateYear;
  @NotBlank(message = "Date may not be empty")
  @Pattern(regexp = "\\d/\\d/\\d\\d\\d\\d", message = "Date must be in the format of mm/dd/yyyy")
  String dateFull;

  String numberInput;
  ArrayList<String> checkboxSet;
  ArrayList<String> checkboxInput;
  String radioInput;
  String selectInput;
  String moneyInput;
  String phoneInput;
  String ssnInput;
  String stateInput;
  @NotEmpty(message = "Please select at least one")
  List<String> favoriteFruitCheckbox;

  @NotBlank(message = "Don't leave this blank")
  @Size(min = 2, message = "You must enter a value 2 characters or longer")
  String inputWithMultipleValidations;

  @NotBlank(message = "Enter a value")
  String inputWithSingleValidation;

  String householdMemberFirstName;
  String householdMemberLastName;
  String householdMemberRelationship;
  String householdMemberRecentlyMovedToUS;
  MultipartFile testFile;
  String dropZoneTestInstance;

  @Positive()
  String validatePositiveIfNotEmpty;

  @Email(message = "Please enter a valid email address.")
  String email;

  String phoneNumber;

  ArrayList<String> howToContactYou;

//    Old approach
//  HashMap<String, HashMap<String, String>> address;
//  HashMap<String, HashMap<String, String>> addressValidated;

  // New approach
  /* Thoughts: writing out all of this sucks. Especially since you would have to
   * do this for each single address instance you want.
   * Let's get the test working and then refactor.
   */
  @NotBlank
  String validationOffStreetAddress1;
  String validationOffStreetAddress2;
  @NotBlank
  String validationOffCity;
  @NotBlank
  String validationOffState;
  @NotBlank
  String validationOffZipCode;

  @NotBlank
  String validationOnStreetAddress1;
  String validationOnStreetAddress2;
  @NotBlank
  String validationOnCity;
  @NotBlank
  String validationOnState;
  @NotBlank
  String validationOnZipCode;
  Boolean useValidatedValidationOn;
}
