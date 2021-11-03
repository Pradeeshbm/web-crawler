/*
 * Copyright (c) 2021. Pradeesh Kumar
 */
package com.pradeesh.crawler.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
/**
 * The type Couchbase properties.
 *
 * @author pradeesh.kumar
 */
@ConstructorBinding
@ConfigurationProperties(prefix = "spring.couchbase")
public record CouchbaseProperties(String connectionString, String username, String password, String bucketName) {
}
