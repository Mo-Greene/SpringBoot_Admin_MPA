package com.mogreene.adminmpa.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 예외처리 핸들러
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * RuntimeExceptionHandler
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    protected ModelAndView handleRuntimeException(RuntimeException e) {

        log.error(e.getMessage());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMsg", e.getMessage());
        modelAndView.setViewName("error");

        return modelAndView;
    }
}
