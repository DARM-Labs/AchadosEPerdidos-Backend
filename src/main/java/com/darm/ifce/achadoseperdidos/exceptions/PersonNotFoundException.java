package com.darm.ifce.achadoseperdidos.exceptions;

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException() {
        super("Person not found");
    }
}
