/*
 * Copyright (c) 2021. Pradeesh Kumar
 */
package org.pradeesh.crawler.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * The type Index response dto class represents the response of user's request to add the url to the index
 *
 * @author pradeesh.kumar
 */
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record IndexResponse(String id, LocalDateTime lastModifiedTime, Status status) {

    public static IndexResponse errorResponse() {
        return new IndexResponse(null, null, Status.ERROR);
    }

    public enum Status {
        CREATED, EXISTS, ERROR
    }
}
