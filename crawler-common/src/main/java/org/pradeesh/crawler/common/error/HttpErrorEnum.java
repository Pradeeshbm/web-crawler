/*
 * Copyright (c) 2021. Pradeesh Kumar
 */

package org.pradeesh.crawler.common.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * The enum contains error message constants and HTTP response code associated with. Also utility methods to create custom exception
 *
 * @author pradeesh.kumar
 */
public enum HttpErrorEnum {

    KAFKA_URL_PRODUCE_ERR("Error occurred while pushing new url event to Kafka", HttpStatus.INTERNAL_SERVER_ERROR),
    ADD_TO_INDEX_ERR("Error occurred while adding the url to the index", HttpStatus.INTERNAL_SERVER_ERROR);;

    private String msg;
    private HttpStatus httpStatus;

    HttpErrorEnum(String msg, HttpStatus httpStatus) {
        this.msg = msg;
        this.httpStatus = httpStatus;
    }

    public ResponseStatusException exception() {
        return createException(null);
    }

    public ResponseStatusException exception(Throwable t) {
        return createException(t);
    }

    private ResponseStatusException createException(Throwable t) {
        return new ResponseStatusException(this.httpStatus, this.msg, t);
    }
}
