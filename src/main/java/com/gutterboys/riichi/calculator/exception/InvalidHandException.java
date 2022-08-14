package com.gutterboys.riichi.calculator.exception;

public class InvalidHandException extends RiichiCalculatorException {

    public InvalidHandException(String errorMessage) {
        super(errorMessage);
    }

    public InvalidHandException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }

}
