/*
 * Copyright (c) 2021. Pradeesh Kumar
 */

package org.pradeesh.crawler.core.listener;

import com.pradeesh.crawler.common.config.KafkaProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.URL;
import org.pradeesh.crawler.core.core.CrawlerEngine;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * Consumes and processes the new url message from the kafka topic
 *
 * @author pradeesh.kumar
 */
@Component
@RequiredArgsConstructor
@Validated
@Slf4j
public class RequestDispatcher {

    private final KafkaProperties kafkaProperties;
    private final CrawlerEngine crawlerEngine;

    @KafkaListener(topics = "${kafka.urlEventTopic}", groupId = "${kafka.consumerGroup}")
    public void onEvent(@Payload @Valid
                            @NotBlank(message = "URL Cannot be blank!")
                            @URL(message = "Invalid URL!") String message) {
        log.info("Received Message in group {}: {}", kafkaProperties.consumerGroup(), message);
        crawlerEngine.crawl(message);
    }
}
