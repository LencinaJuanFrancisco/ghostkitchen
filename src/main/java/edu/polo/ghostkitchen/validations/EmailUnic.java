package edu.polo.ghostkitchen.validations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;
import jakarta.validation.*;


@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailUnicValidator.class)
@Documented
public @interface EmailUnic {
        String message() default "Ya existe Usuario con este Email";
         Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default{};
}
