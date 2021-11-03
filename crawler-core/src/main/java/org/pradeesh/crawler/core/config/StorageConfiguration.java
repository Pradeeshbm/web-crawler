/*
 * Copyright (c) 2021. Pradeesh Kumar
 */

package org.pradeesh.crawler.core.config;

import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

/**
 * The type Storage configuration.
 *
 * @author pradeesh.kumar
 */
@Configuration
@ConfigurationProperties(prefix = "crawler.storage.minio")
@Slf4j
public record StorageConfiguration(@NotNull String url, @NotNull String accessKey, @NotNull String secretKey, @NotNull String docBucket, @NotNull String docPath) {

    /**
     * Create the Minio client bean
     *
     * @return the minio client
     */
    @Bean
    public MinioClient minioClient() {
        log.info("Creating MinIO Client with the url: {}", this.url);
        MinioClient client = MinioClient.builder()
                .endpoint(this.url)
                .credentials(this.accessKey, this.secretKey)
                .build();
        log.info("MinIo client has been created successfully");
        return client;
    }
}
