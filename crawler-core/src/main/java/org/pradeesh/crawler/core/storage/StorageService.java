/*
 * Copyright (c) 2021. Pradeesh Kumar
 */

package org.pradeesh.crawler.core.storage;

import org.jsoup.nodes.Document;
import org.pradeesh.crawler.core.error.StorageException;
import org.pradeesh.crawler.core.message.WebDocument;

/**
 * The interface Storage service.
 *
 * @author pradeesh.kumar
 */
public interface StorageService {

    /**
     * Stores the specified HTML document in the storage.
     *
     * @param htmlDoc the html doc
     * @return the object name
     *
     * @throws StorageException If any error occurrs while storing the document
     */
    String store(WebDocument htmlDoc);
}
