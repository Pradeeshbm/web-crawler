/*
 * Copyright (c) 2021. Pradeesh Kumar
 */
package org.pradeesh.crawler.crawlerweb.event.producer;

/**
 * The interface Url event producer.
 *
 * @author pradeesh.kumar
 */
public interface UrlEventProducer {

    /**
     * Emits the message to the message factory.
     *
     * @param url the url
     */
    void emit(String url);
}
