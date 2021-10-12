/*
 * Copyright (c) 2021. Pradeesh Kumar
 */
package org.pradeesh.crawler.crawlerweb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.IdAttribute;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * The type Url index database model.
 *
 * @author pradeesh.kumar
 */
@Getter @Setter
@AllArgsConstructor
@Document
public class UrlIndex {

    @IdAttribute
    private String id;
    @URL @NotNull
    private String url;
    @NotBlank
    private String docId;
    @CreatedDate @NotNull
    private LocalDateTime createdTime;
    @LastModifiedDate @NotNull
    private LocalDateTime lastModifiedTime;
}
