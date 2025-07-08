package com.sanbro.AuthenticationService.exception;

public class UserAlreadyPresentException extends RuntimeException{
    public UserAlreadyPresentException(String message){
        super(message);
    }
}
