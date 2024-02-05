package com.techinnoura.ticketsystem.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.lang.model.type.ErrorType;

@RestControllerAdvice
public class TicketExceptionHandler {



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserException.class)
    public ErrorResponse handleBadRequest(UserException e){
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TicketException.class)
    public ErrorResponse handleBadRequest(TicketException e){
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),e.getMessage());
    }
}
