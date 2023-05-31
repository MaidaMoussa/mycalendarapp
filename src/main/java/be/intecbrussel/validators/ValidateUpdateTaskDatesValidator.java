package be.intecbrussel.validators;

import be.intecbrussel.dtos.UpdateTaskRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidateUpdateTaskDatesValidator implements ConstraintValidator<ValidateTaskDates, UpdateTaskRequest> {
    @Override
    public void initialize(ValidateTaskDates constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UpdateTaskRequest updateTaskRequest, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = true;


        return isValid;
    }
}
