package com.gutterboys.riichi.calculator.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HandContentConstraintValidator implements ConstraintValidator<HandContentConstraint, List<Integer>> {

    @Override
    public boolean isValid(List<Integer> tiles, ConstraintValidatorContext context) {
        return tiles.stream().filter(tile -> tile > 36 || tile < 0).count() == 0;
    }

}
