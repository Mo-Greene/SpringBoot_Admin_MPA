package com.mogreene.adminmpa.common.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 공통 response
 * @author mogreene
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDTO<T> {

    /**
     * response HttpStatus
     */
    private HttpStatus httpStatus;

    /**
     * response result
     */
    private T data;
}
