package be.intecbrussel.validators;

import be.intecbrussel.dtos.UpdateTaskRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.format.DateTimeFormatter;

public class ValidateUpdateTaskDatesValidator implements ConstraintValidator<ValidateTaskDates, UpdateTaskRequest> {

    private static final String DATE_PATTERN = "dd/MM/yyyy";

    @Override
    public void initialize(ValidateTaskDates constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UpdateTaskRequest req, ConstraintValidatorContext context) {
        boolean isValid = true;

        if (req.getStartDate() == null) {
            isValid = false;
            context.buildConstraintViolationWithTemplate("A task must always have a start date")
                    .addConstraintViolation();

        } else if (!req.isFullDay() && req.getEndDate() == null) {
            isValid = false;
            context.buildConstraintViolationWithTemplate("A non full day task must always have an end date")
                    .addConstraintViolation();

        } else if (req.getEndDate() != null) {

            if (req.getStartDate().isAfter(req.getEndDate())) {
                isValid = false;
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
                context.buildConstraintViolationWithTemplate("A task start date must be before its end date " +
                                "given : start '" + req.getStartDate().format(dateTimeFormatter)
                                + "' end '" + req.getEndDate().format(dateTimeFormatter) + "'")
                        .addConstraintViolation();
            }
        }

        return isValid;
    }
}
