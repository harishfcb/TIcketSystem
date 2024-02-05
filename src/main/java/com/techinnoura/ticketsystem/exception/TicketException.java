package com.techinnoura.ticketsystem.exception;

import org.springframework.http.HttpStatus;

public class TicketException extends Exception {
    private HttpStatus statusCode;

    public TicketException(String message, HttpStatus statusCode){
        super(message);
        this.statusCode = statusCode;


    }
}
