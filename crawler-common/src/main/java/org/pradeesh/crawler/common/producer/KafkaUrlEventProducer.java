/*
 * Copyright (c) 2021. Pradeesh Kumar
 */
package org.pradeesh.crawler.common.producer;

import org.pradeesh.crawler.common.config.KafkaProperties;
import org.pradeesh.crawler.common.error.HttpErrorEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * The type Kafka url event producer produces messages to the kafka message broker.
 *
 * @author pradeesh.kumar
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaUrlEventProducer implements UrlEventProducer {

    private final KafkaProperties kafkaProperties;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void emit(String url) {
        try {
            log.info("Producing new url event to the kafka topic: {} for the url: {}", kafkaProperties.urlEventTopic(), url);
            kafkaTemplate.send(kafkaProperties.urlEventTopic(), url, url);
            log.info("Url {} has been pushed to the kafka topic: {}", url, kafkaProperties.urlEventTopic());
        } catch (RuntimeException e) {
            log.error("Error occurred while pushing the url: {} to the kafka topic: {}", url, kafkaProperties.urlEventTopic(), e);
            throw HttpErrorEnum.KAFKA_URL_PRODUCE_ERR.exception(e);
        }
    }
}
