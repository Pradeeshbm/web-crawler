/*
 * Copyright (c) 2021. Pradeesh Kumar
 */

package org.pradeesh.crawler.core.core;

import org.pradeesh.crawler.core.message.WebDocument;

/**
 * The interface Document service.
 *
 * @author pradeesh.kumar
 */
public interface CrawlerWebClient {

    /**
     * Get document.
     *
     * @param url the url
     * @return the document
     */
    WebDocument get(String url);
}
