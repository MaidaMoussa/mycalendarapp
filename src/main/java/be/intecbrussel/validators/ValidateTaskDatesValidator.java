package be.intecbrussel.validators;

import be.intecbrussel.models.Task;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidateTaskDatesValidator implements ConstraintValidator<ValidateTaskDates, Task> {


    @Override
    public void initialize(ValidateTaskDates constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Task task, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = true;


        return isValid;
    }
}
