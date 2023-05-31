package be.intecbrussel.validators;

import be.intecbrussel.models.Task;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.format.DateTimeFormatter;

public class ValidateTaskDatesValidator implements ConstraintValidator<ValidateTaskDates, Task> {

    private static final String DATE_PATTERN = "dd/MM/yyyy";

    @Override
    public void initialize(ValidateTaskDates constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Task task, ConstraintValidatorContext context) {
        boolean isValid = true;

        if (task.getStartDate() == null) {
            isValid = false;
            context.buildConstraintViolationWithTemplate("A task must always have a start date")
                    .addConstraintViolation();

        } else if (!task.isFullDay() && task.getEndDate() == null) {
            isValid = false;
            context.buildConstraintViolationWithTemplate("A non full day task must always have an end date")
                    .addConstraintViolation();

        } else if (task.getEndDate() != null) {

            if (task.getStartDate().isAfter(task.getEndDate())) {
                isValid = false;
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
                context.buildConstraintViolationWithTemplate("A task start date must be before its end date " +
                                "given : start '" + task.getStartDate().format(dateTimeFormatter)
                                + "' end '" + task.getEndDate().format(dateTimeFormatter) + "'")
                        .addConstraintViolation();
            }
        }

        return isValid;
    }
}
