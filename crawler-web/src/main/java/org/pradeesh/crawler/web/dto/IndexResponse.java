/*
 * Copyright (c) 2021. Pradeesh Kumar
 */
package org.pradeesh.crawler.web.dto;

import lombok.Builder;

import java.time.LocalDateTime;

/**
 * The type Index response dto class represents the response of user's request to add the url to the index
 *
 * @author pradeesh.kumar
 */
@Builder
public record IndexResponse(String id, LocalDateTime lastModifiedTime, Status status) {

    public enum Status {
        CREATED, EXISTS
    }
}
