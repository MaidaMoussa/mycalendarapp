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
    public boolean isValid(UpdateTaskRequest req, ConstraintValidatorContext context) {
        boolean isValid = true;


        if (req.isFullDay()) {
            if (req.getStartTime() != null || req.getEndTime() != null) {
                isValid = false;
                context.buildConstraintViolationWithTemplate("A  full day task must not have a start time and an end time ")
                        .addConstraintViolation();
            }
        } else {
            if (req.getStartTime() == null || req.getEndTime() == null) {
                isValid = false;
                context.buildConstraintViolationWithTemplate("A non full day task must have a start time and an end time ")
                        .addConstraintViolation();
            }
        }

        return isValid;
    }
}
