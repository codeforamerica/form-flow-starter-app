package org.formflowstartertemplate.app.inputs;

import formflow.library.data.FlowInputs;
import formflow.library.data.validators.Money;
import formflow.library.data.validators.Phone;
import formflow.library.utils.RegexUtils;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import java.util.ArrayList;
import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("unused")
public class Ubi extends FlowInputs {

  private MultipartFile ubiFiles;

  // Language Preferences Screen
  private String languageRead;
  private String languageSpoken;
  private String needInterpreter;

  // Personal Info Screen
  @NotBlank(message = "{personal-info.provide-first-name}")
  private String firstName;
  @NotBlank(message = "{personal-info.provide-last-name}")
  private String lastName;
  @NotBlank(message = "{personal-info.provide-birth-day}")
  @Min(value = 1, message = "{personal-info.provide-day-min}")
  @Max(value = 31, message = "{personal-info.provide-day-max}")
  private String birthDay;
  @NotBlank(message = "{personal-info.provide-birth-month}")
  @Min(value = 1, message = "{personal-info.provide-month-min}")
  @Max(value = 12, message = "{personal-info.provide-month-max}")
  private String birthMonth;
  @NotBlank(message = "{personal-info.provide-birth-year}")
  @Min(value = 1850, message = "{personal-info.provide-year-min}")
  @Max(value = 2100, message = "{personal-info.provide-year-max}")
  private String birthYear;
  @Pattern(regexp = "\\d{1,2}/\\d{1,2}/\\d\\d\\d\\d", message = "{personal-info.date-invalid}")
  private String birthDate;

  private String genderIdentity;
  // TODO: figure out how to only have day & month for a date fragment
  private String movedToUSA;
  private String movedToUSADay;
  private String movedToUSAMonth;
  private String movedToUSAYear;
  @Pattern(regexp = "\\d{1,2}/\\d{1,2}/\\d\\d\\d\\d", message = "{personal-info.date-invalid}")
  private String movedToUSADate;
  private String movedFromCountry;

  // Home Address Screen
  @NotBlank
  private String residentialAddressStreetAddress1;
  private String residentialAddressStreetAddress2;
  @NotBlank
  private String residentialAddressCity;
  @NotBlank
  private String residentialAddressState;
  @NotBlank
  private String residentialAddressZipCode;

  // Verify Home Address Screen
  private String useSuggestedResidentialAddress;

  // Housemates Screen
  private String hasHousehold;

  // Housemate Info Screen
  @NotBlank
  private String householdMemberFirstName;
  @NotBlank
  private String householdMemberLastName;
  private String householdMemberRelationship;
  private String householdMemberRecentlyMovedToUS;
  private String householdMemberMovedToUSADay;
  private String householdMemberMovedToUSAMonth;
  private String householdMemberMovedToUSAYear;
  @Pattern(regexp = "\\d{1,2}/\\d{1,2}/\\d\\d\\d\\d", message = "{personal-info.date-invalid}")
  private String householdMemberMovedToUSADate;
  private String householdMemberMovedFromCountry;

  // Household Member Income Screen
  @NotBlank(message = "{household-member-income.failed-to-make-selection}")
  private String householdMember;

  // Income Types Screen
  @NotEmpty(message = "{income-types.error}")
  private ArrayList<String> incomeTypes;

  // Income Amounts Screen
  @NotBlank(message = "{income-amounts.must-select-one}")
  @Money(message = "{income-amounts.must-be-dollars-cents}")
  private String incomeJobAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @Money(message = "{income-amounts.must-be-dollars-cents}")
  private String incomeSelfAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @Money(message = "{income-amounts.must-be-dollars-cents}")
  private String incomeUnemploymentAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @Money(message = "{income-amounts.must-be-dollars-cents}")
  private String incomeSocialSecurityAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @Money(message = "{income-amounts.must-be-dollars-cents}")
  private String incomeRetirementAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @Money(message = "{income-amounts.must-be-dollars-cents}")
  private String incomeChildOrSpousalSupportAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @Money(message = "{income-amounts.must-be-dollars-cents}")
  private String incomePensionAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @Money(message = "{income-amounts.must-be-dollars-cents}")
  private String incomeInvestmentAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @Money(message = "{income-amounts.must-be-dollars-cents}")
  private String incomeCapitalGainsAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @Money(message = "{income-amounts.must-be-dollars-cents}")
  private String incomeRentalOrRoyaltyAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @Money(message = "{income-amounts.must-be-dollars-cents}")
  private String incomeFarmOrFishAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @Money(message = "{income-amounts.must-be-dollars-cents}")
  private String incomeAlimonyAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @Money(message = "{income-amounts.must-be-dollars-cents}")
  private String incomeTaxableScholarshipAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @Money(message = "{income-amounts.must-be-dollars-cents}")
  private String incomeCancelledDebtAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @Money(message = "{income-amounts.must-be-dollars-cents}")
  private String incomeCourtAwardsAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @Money(message = "{income-amounts.must-be-dollars-cents}")
  private String incomeGamblingAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @Money(message = "{income-amounts.must-be-dollars-cents}")
  private String incomeJuryDutyPayAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @Money(message = "{income-amounts.must-be-dollars-cents}")
  private String incomeOtherAmount;

  // Reported Household Annual Income Screen
  @NotBlank(message = "{household-reported-annual-pre-tax-income.please-enter-a-value}")
  @Money(message = "{income-amounts.must-be-dollars-cents}")
  private String reportedTotalAnnualHouseholdIncome;

  //Economic Hardship Screen
  private ArrayList<String> economicHardshipTypes;

  // Legal Stuff Screen
  @NotEmpty(message = "{legal-stuff.make-sure-you-answer-this-question}")
  private ArrayList<String> agreesToLegalTerms;
  @NotBlank
  private String signature;

  // Contact Info Screen
  @Phone(message = "{contact-info.invalid-phone-number}")
  private String phoneNumber;
  @Email(message = "{contact-info.invalid-email}", regexp = RegexUtils.EMAIL_REGEX)
  private String email;
  @NotEmpty(message = "{contact-info.select-a-contact-method}")
  private ArrayList<String> howToContactYou;
}
