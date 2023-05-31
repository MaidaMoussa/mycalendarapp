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
    public boolean isValid(CreateNewTaskRequest req, ConstraintValidatorContext context) {
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
