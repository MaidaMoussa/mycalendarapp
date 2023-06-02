package be.intecbrussel.validators;

import be.intecbrussel.dtos.CreateNewTaskRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.format.DateTimeFormatter;

public class ValidateCreateTaskHoursValidator implements ConstraintValidator<ValidateTaskHours, CreateNewTaskRequest> {

    private static final String DATE_PATTERN = "dd/MM/yyyy";

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
            } else {

                if (req.getStartDate().isEqual(req.getEndDate())) {

                    if (!req.getEndTime().isAfter(req.getStartTime())) {
                        isValid = false;
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);

                        context.buildConstraintViolationWithTemplate("For same day tasks start time must be before end time given : " +
                                        "start date '" + req.getStartDate().format(dateTimeFormatter)
                                        + "' start time  '" + req.getStartTime()
                                        + "' end time '" + req.getEndTime() + "'")
                                .addConstraintViolation();
                    }
                }
            }
        }

        return isValid;
    }
}
