/*
 * Copyright (c) 2021. Pradeesh Kumar
 */

package org.pradeesh.crawler.core.error;

/**
 * The type Storage exception.
 *
 * @author pradeesh.kumar
 */
public class StorageException extends RuntimeException {

    /**
     * Instantiates a new Storage exception.
     *
     * @param msg the msg
     * @param t   the throwable
     */
    public StorageException(String msg, Throwable t) {
        super(msg, t);
    }
}
