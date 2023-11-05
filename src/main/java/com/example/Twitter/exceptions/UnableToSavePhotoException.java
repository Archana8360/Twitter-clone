package com.example.Twitter.exceptions;

public class UnableToSavePhotoException extends Exception{

    private static final long serialVersionUID = 1L;

    public UnableToSavePhotoException(){
        super("Unable to save the supplied photo");
    }
}
