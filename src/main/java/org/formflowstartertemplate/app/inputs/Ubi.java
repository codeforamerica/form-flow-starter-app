package org.formflowstartertemplate.app.inputs;

import formflow.library.data.validators.FormFlowEmail;
import formflow.library.data.validators.Money;
import formflow.library.data.FlowInputs;
import formflow.library.data.validators.Phone;
import java.util.ArrayList;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
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
  @Min(value = 1, message = "{personal-info.provide-birth-day-min}")
  @Max(value = 31, message = "{personal-info.provide-birth-day-max}")
  private String birthDay;
  @Min(value = 1, message = "{personal-info.provide-birth-month-min}")
  @Max(value = 12, message = "{personal-info.provide-birth-month-max}")
  private String birthMonth;
  @Min(value = 1850, message = "{personal-info.provide-birth-year-min}")
  @Max(value = 2100, message = "{personal-info.provide-birth-year-max}")
  private String birthYear;
  private String genderIdentity;
  private String movedToUSA;
  private String movedToUSADay;
  private String movedToUSAMonth;
  // TODO: figure out how to only have day & month for a date fragment
  private String movedToUSAYear;
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
  private String useValidatedResidentialAddress;

  // Housemates Screen
  private String hasHousehold;

  // Housemate Info Screen
  @NotBlank
  private String householdMemberFirstName;
  @NotBlank
  private String householdMemberLastName;
  private String householdMemberRelationship;
  private String householdMemberRecentlyMovedToUS;
  private String householdAddressFile;  // file id

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

  @NotEmpty(message="{legal-stuff.make-sure-you-answer-this-question}")
  private ArrayList<String> agreesToLegalTerms;
  @NotBlank
  private String signature;
  @Phone(message="{contact-info.invalid-phone-number}")
  private String phoneNumber;
  @FormFlowEmail(message="{contact-info.invalid-email}")
  private String email;
  @NotEmpty
  private ArrayList<String> howToContactYou;


}
