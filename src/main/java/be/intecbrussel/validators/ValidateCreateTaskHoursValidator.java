package be.intecbrussel.validators;

import be.intecbrussel.dtos.CreateNewTaskRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidateCreateTaskHoursValidator implements ConstraintValidator<ValidateTaskHours, CreateNewTaskRequest> {
    @Override
    public void initialize(ValidateTaskHours constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(CreateNewTaskRequest createNewTaskRequest, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = true;


        return isValid;
    }
}
