package com.ys.shiro.config;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Component
public class MyExceptionHandler {
    @ResponseBody
    @ExceptionHandler(BindException.class)
    public Map<String, Object> defaultExceptionHandler(BindException bindException, HttpServletResponse response) {
        //处理返回的错误信息
        Map<String, Object> resultMap = new HashMap<>();
        StringBuffer errorMsg = new StringBuffer();
        List<FieldError> fieldErrors = bindException.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            errorMsg.append(fieldError.getObjectName()).append(".").append(fieldError.getField()).append(fieldError.getDefaultMessage()).append(";");
        }
        resultMap.put("status", 400);
        resultMap.put("message", errorMsg.toString());
        return resultMap;
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, Object> defaultExceptionHandler(ConstraintViolationException constraintViolationException) {
        //处理返回的错误信息
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", 400);
        resultMap.put("message", constraintViolationException.getMessage());
        return resultMap;
    }

    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public Map<String, Object> defaultExceptionHandler(AuthorizationException authorizationException) {
        //处理返回的错误信息
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", 400);
        resultMap.put("message", authorizationException.getMessage());
        return resultMap;
    }
}
