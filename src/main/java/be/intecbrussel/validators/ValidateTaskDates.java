package be.intecbrussel.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {ValidateTaskDatesValidator.class,
        ValidateUpdateTaskDatesValidator.class,
        ValidateCreateTaskDatesValidator.class})
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateTaskDates {
    String message() default "Task dates validation failed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
