/*
 * Copyright (c) 2021. Pradeesh Kumar
 */
package org.pradeesh.crawler.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * The type Global exception handler.
 *
 * @author pradeesh.kumar
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handle request body error response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<String>> handleRequestBodyError(WebExchangeBindException e) {
        log.error("Error occurred in request body", e);
        String error = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .sorted()
                .collect(Collectors.joining(", "));
        log.error("Errors: {}", error);
        return Mono.just(ResponseEntity.badRequest().body(error));
    }

    /**
     * Handle constraint violation error response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Mono<ResponseEntity<String>> handleConstraintViolationError(ConstraintViolationException e) {
        log.error("Error occurred in request body", e);
        String error = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.joining(", "));
        log.error("Errors: {}", error);
        return Mono.just(ResponseEntity.badRequest().body(error));
    }
}
