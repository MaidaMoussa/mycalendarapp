package be.intecbrussel.validators;

import be.intecbrussel.models.Task;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidateTaskHoursValidator implements ConstraintValidator<ValidateTaskHours, Task> {
    @Override
    public void initialize(ValidateTaskHours constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Task task, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = true;


        return isValid;
    }
}
