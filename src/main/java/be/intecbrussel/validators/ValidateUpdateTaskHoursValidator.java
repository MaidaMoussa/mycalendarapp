package be.intecbrussel.validators;

import be.intecbrussel.dtos.UpdateTaskRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidateUpdateTaskHoursValidator implements ConstraintValidator<ValidateTaskHours, UpdateTaskRequest> {
    @Override
    public void initialize(ValidateTaskHours constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UpdateTaskRequest updateTaskRequest, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = true;


        return isValid;
    }
}
