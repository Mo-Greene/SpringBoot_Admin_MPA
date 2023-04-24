package com.mogreene.adminmpa.common.exception;

import com.mogreene.adminmpa.common.api.ApiResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
    protected ResponseEntity<ApiResponseDTO<?>> handleRuntimeException(RuntimeException e) {

        // TODO: 2023/04/24 서버 시작하고 에러가 한번뜸
        log.error(e.getMessage());
        e.getStackTrace();

        ApiResponseDTO<?> apiResponseDTO = ApiResponseDTO.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .data(e.getMessage())
                .build();

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.BAD_REQUEST);
    }
}
