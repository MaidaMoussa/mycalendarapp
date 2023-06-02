package be.intecbrussel.validators;

import be.intecbrussel.models.Task;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.format.DateTimeFormatter;

public class ValidateTaskHoursValidator implements ConstraintValidator<ValidateTaskHours, Task> {

    private static final String DATE_PATTERN = "dd/MM/yyyy";

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
            if (task.getStartDate().isEqual(task.getEndDate())) {

                if (!task.getEndTime().isAfter(task.getStartTime())) {
                    isValid = false;
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);

                    context.buildConstraintViolationWithTemplate("For same day tasks start time must be before end time given : " +
                                    "start date '" + task.getStartDate().format(dateTimeFormatter)
                                    + "' start time  '" + task.getStartTime()
                                    + "' end time '" + task.getEndTime() + "'")
                            .addConstraintViolation();
                }
            }

        }

        return isValid;
    }
}
