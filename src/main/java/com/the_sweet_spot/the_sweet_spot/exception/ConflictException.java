package com.the_sweet_spot.the_sweet_spot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException{
    public ConflictException(String message){
        super(message);
    }
}
