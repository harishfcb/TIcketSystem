package com.techinnoura.ticketsystem.exception;

import org.springframework.http.HttpStatus;

public class UserException extends Exception{
    private  HttpStatus statusCode;

    public UserException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
