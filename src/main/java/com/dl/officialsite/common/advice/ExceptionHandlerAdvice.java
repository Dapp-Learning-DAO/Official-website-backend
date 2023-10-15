package com.dl.officialsite.common.advice;


import com.dl.officialsite.common.model.ServerResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Component
@Slf4j
public class ExceptionHandlerAdvice {


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ServerResponse handleMethodArgumentNotValidException(HttpServletRequest req, Exception e)
            throws JsonProcessingException {
        log.error("", e);
        return ServerResponse.failWithReason("99999", e.getMessage());
    }

}
