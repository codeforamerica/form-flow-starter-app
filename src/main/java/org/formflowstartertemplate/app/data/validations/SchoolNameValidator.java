package org.formflowstartertemplate.app.data.validations;

import java.util.Arrays;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.formflowstartertemplate.app.data.SchoolNameRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class SchoolNameValidator implements ConstraintValidator<SchoolNameIsValid, String> {
    @Autowired
    SchoolNameRepositoryService repositoryService; // <-- THIS WORKS IF YOU CHANGE IT TO BE A SubmissionRepositoryService (along with commmenting out the logic in isValid)

    @Override
    public boolean isValid(String schoolName, ConstraintValidatorContext context) {
        return Arrays.stream(repositoryService.listValidSchoolNames()).anyMatch(name -> name.equals(schoolName));
    }
}
