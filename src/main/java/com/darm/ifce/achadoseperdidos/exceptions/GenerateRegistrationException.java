package com.darm.ifce.achadoseperdidos.exceptions;

public class GenerateRegistrationException extends RuntimeException{
    public GenerateRegistrationException() {
        super("We have detected an error in generating the registration number. Please try again.");
    }
}
