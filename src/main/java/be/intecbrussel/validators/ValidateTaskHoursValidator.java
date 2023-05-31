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
    public boolean isValid(Task task, ConstraintValidatorContext context) {
        boolean isValid = true;


        if (task.isFullDay()) {
            if (task.getStartTime() != null || task.getEndTime() != null) {
                isValid = false;
                context.buildConstraintViolationWithTemplate("A  full day task must not have a start time and an end time ")
                        .addConstraintViolation();
            }
        } else {
            if (task.getStartTime() == null || task.getEndTime() == null) {
                isValid = false;
                context.buildConstraintViolationWithTemplate("A non full day task must have a start time and an end time ")
                        .addConstraintViolation();
            }
        }

        return isValid;
    }
}
