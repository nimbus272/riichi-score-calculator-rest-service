package com.gutterboys.riichi.calculator.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HandSizeConstraintValidator implements ConstraintValidator<HandSizeConstraint, List<Integer>>{

    @Override
    public boolean isValid(List<Integer> tiles, ConstraintValidatorContext context) {
        return tiles.size() > 13;
    }

    
    
}
