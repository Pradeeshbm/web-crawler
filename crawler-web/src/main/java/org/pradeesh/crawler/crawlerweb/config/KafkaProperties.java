/*
 * Copyright (c) 2021. Pradeesh Kumar
 */
package org.pradeesh.crawler.crawlerweb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * The type Kafka properties
 *
 * @author pradeesh.kumar
 */
@ConstructorBinding
@ConfigurationProperties(prefix = "kafka")
public record KafkaProperties(String bootstrapAddress, String urlEventTopic) {
}
