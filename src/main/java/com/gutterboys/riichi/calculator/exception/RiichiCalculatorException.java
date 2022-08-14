package com.gutterboys.riichi.calculator.exception;

public class RiichiCalculatorException extends Exception {

    public RiichiCalculatorException(String errorMessage) {
        super(errorMessage);
    }

    public RiichiCalculatorException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }

}
