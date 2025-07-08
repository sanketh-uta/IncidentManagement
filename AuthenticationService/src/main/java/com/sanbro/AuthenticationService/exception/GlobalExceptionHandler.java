package com.sanbro.AuthenticationService.exception;

import com.sanbro.AuthenticationService.dto.ExceptionDto;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyPresentException.class)
    public ResponseEntity<?> userAlreadyExist(UserAlreadyPresentException exception, HttpServletRequest http){
        ExceptionDto userExists =
                new ExceptionDto(exception.getMessage(),"user already exists",
                        HttpStatus.BAD_REQUEST.value(), http.getRequestURI());

        return new ResponseEntity<>(userExists,HttpStatus.BAD_REQUEST);

    }
}
