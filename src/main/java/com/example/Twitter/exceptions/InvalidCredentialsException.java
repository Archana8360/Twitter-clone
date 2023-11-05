package com.example.Twitter.exceptions;

public class InvalidCredentialsException extends Exception{

    public InvalidCredentialsException(){
        super("Username or Password doesn't exist");
    }
}
