package com.gutterboys.riichi.calculator.validation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = HandContentConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface HandContentConstraint {

    String message() default "The input hand contains integers that are out of bounds.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
