/*
 * Copyright (c) 2021. Pradeesh Kumar
 */

package org.pradeesh.crawler.core.core;

/**
 * The interface CrawlerEngine provides specification for the crawler
 *
 * @author pradeesh.kumar
 */
public interface CrawlerEngine {

    /**
     * Processes the url
     *
     * @param url the url
     */
    void crawl(String url);
}
