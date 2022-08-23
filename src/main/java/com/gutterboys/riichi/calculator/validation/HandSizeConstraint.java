package com.gutterboys.riichi.calculator.validation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = HandSizeConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface HandSizeConstraint {
    String message() default "The input hand cannot contain less than 14 tiles.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
