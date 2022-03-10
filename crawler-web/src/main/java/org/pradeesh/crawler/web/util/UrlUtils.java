/*
 * Copyright (c) 2021. Pradeesh Kumar
 */
package org.pradeesh.crawler.web.util;

/**
 * The type Url utils contains utility methods for URL related operations
 *
 * @author pradeesh.kumar
 */
public final class UrlUtils {

    private static final char CHAR_HASH = '#';

    private UrlUtils() {}

    /**
     * Removes the hash part of the url and returns the rest of the url
     *
     * @return the string
     */
    public static String removeHash(String url) {
        int hashIndex = url.indexOf(CHAR_HASH);
        if (hashIndex != -1) {
            url = url.substring(0, hashIndex - 1);
        }
        return url;
    }
}
