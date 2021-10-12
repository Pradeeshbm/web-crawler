/*
 * Copyright (c) 2021. Pradeesh Kumar
 */
package org.pradeesh.crawler.crawlerweb.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableReactiveCouchbaseRepositories;

/**
 * The type Database configuration.
 *
 * @author pradeesh.kumar
 */
@Configuration
@RequiredArgsConstructor
@EnableReactiveCouchbaseRepositories(basePackages = "org.pradeesh.crawler.crawlerweb.repository")
public class DatabaseConfig extends AbstractCouchbaseConfiguration {

    private final CouchbaseProperties couchbaseProperties;

    @Override
    public String getConnectionString() {
        return couchbaseProperties.connectionString();
    }

    @Override
    public String getUserName() {
        return couchbaseProperties.username();
    }

    @Override
    public String getPassword() {
        return couchbaseProperties.password();
    }

    @Override
    public String getBucketName() {
        return couchbaseProperties.bucketName();
    }

}
