package com.example.Twitter.exceptions;

public class IncorrectVerificationCodeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IncorrectVerificationCodeException(){
        super("The code passed does not match the verification code.");
    }

}
