package com.csaba79coder.bookliquibase.domain.controller.exception;

import com.csaba79coder.bookliquibase.domain.controller.exception.value.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.NoSuchElementException;

import static com.csaba79coder.bookliquibase.domain.controller.exception.value.ErrorCode.ERROR_CODE_001;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex) {
        return new ResponseEntity<>(responseBodyWithMessage(ERROR_CODE_001, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    private String responseBodyWithMessage(ErrorCode code, String message) {
        return Map.of(code, message).toString();
       // return new HashMap<>() {{   put("sfsfs", "fsfsf");       }};
        //return code + " : " + message;
    }
}
