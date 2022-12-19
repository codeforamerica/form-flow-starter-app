package org.formflowstartertemplate.app.inputs;

import java.util.ArrayList;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class Ubi {

  // Needs to be declared since Spring Security inserts _csrf as a hidden field to all forms
  private String _csrf;

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
  @Digits(integer = 2, fraction = 0, message = "{personal-info.provide-birth-day}")
  private String birthDay;
  @Digits(integer = 2, fraction = 0, message = "{personal-info.provide-birth-month}")
  private String birthMonth;
  @Digits(integer = 4, fraction = 0, message = "{personal-info.provide-birth-year}")
  private String birthYear;
  private String genderIdentity;
  private String movedToUSA;
  @Digits(integer = 2, fraction = 0, message = "{personal-info.provide-moved-to-day}")
  private String movedToUSADay;
  @Digits(integer = 2, fraction = 0, message = "{personal-info.provide-moved-to-month}")
  private String movedToUSAMonth;
  @Digits(integer = 4, fraction = 0, message = "{personal-info.provide-moved-to-year}")
  // TODO: figure out how to only have day & month for a date fragment
  private String movedToUSAYear;
  private String movedFromCountry;

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
  @DecimalMin(value = "0.0", message = "{income-amounts.must-be-numbers}")
  private String incomeJobAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @DecimalMin(value = "0.0", message = "{income-amounts.must-be-numbers}")
  private String incomeSelfAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @DecimalMin(value = "0.0", message = "{income-amounts.must-be-numbers}")
  private String incomeUnemploymentAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @DecimalMin(value = "0.0", message = "{income-amounts.must-be-numbers}")
  private String incomeSocialSecurityAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @DecimalMin(value = "0.0", message = "{income-amounts.must-be-numbers}")
  private String incomeRetirementAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @DecimalMin(value = "0.0", message = "{income-amounts.must-be-numbers}")
  private String incomeChildOrSpousalSupportAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @DecimalMin(value = "0.0", message = "{income-amounts.must-be-numbers}")
  private String incomePensionAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @DecimalMin(value = "0.0", message = "{income-amounts.must-be-numbers}")
  private String incomeInvestmentAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @DecimalMin(value = "0.0", message = "{income-amounts.must-be-numbers}")
  private String incomeCapitalGainsAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @DecimalMin(value = "0.0", message = "{income-amounts.must-be-numbers}")
  private String incomeRentalOrRoyaltyAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @DecimalMin(value = "0.0", message = "{income-amounts.must-be-numbers}")
  private String incomeFarmOrFishAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @DecimalMin(value = "0.0", message = "{income-amounts.must-be-numbers}")
  private String incomeAlimonyAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @DecimalMin(value = "0.0", message = "{income-amounts.must-be-numbers}")
  private String incomeTaxableScholarshipAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @DecimalMin(value = "0.0", message = "{income-amounts.must-be-numbers}")
  private String incomeCancelledDebtAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @DecimalMin(value = "0.0", message = "{income-amounts.must-be-numbers}")
  private String incomeCourtAwardsAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @DecimalMin(value = "0.0", message = "{income-amounts.must-be-numbers}")
  private String incomeGamblingAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @DecimalMin(value = "0.0", message = "{income-amounts.must-be-numbers}")
  private String incomeJuryDutyPayAmount;
  @NotBlank(message = "{income-amounts.must-select-one}")
  @DecimalMin(value = "0.0", message = "{income-amounts.must-be-numbers}")
  private String incomeOtherAmount;

  // Reported Household Annual Income Screen
  @NotBlank(message = "{household-reported-annual-pre-tax-income.please-enter-a-value}")
  @DecimalMin(value = "0.0", message = "{household-reported-annual-pre-tax-income.must-be-a-valid-amount}")
  private String reportedTotalAnnualHouseholdIncome;
}
