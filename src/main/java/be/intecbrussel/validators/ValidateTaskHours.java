package be.intecbrussel.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {ValidateTaskHoursValidator.class,
        ValidateUpdateTaskHoursValidator.class,
        ValidateCreateTaskHoursValidator.class})
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateTaskHours {

    String message() default "Task hours validation failed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
