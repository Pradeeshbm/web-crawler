/*
 * Copyright (c) 2021. Pradeesh Kumar
 */

package com.pradeesh.crawler.common.util;

import com.pradeesh.crawler.common.message.UrlIndex;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * The type Crawler utils contains static utility methods.
 *
 * @author pradeesh.kumar
 */
public final class CrawlerUtils {

    private CrawlerUtils() {}

    /**
     * Is null or expired boolean.
     *
     * @param index the index
     * @return the boolean
     */
    public static boolean isNullOrExpired(UrlIndex index) {
        return index == null || isExpired(index);
    }

    private static boolean isExpired(UrlIndex index) {
        long days = ChronoUnit.DAYS.between(LocalDateTime.now(), index.getLastModifiedTime());
        return days >= 10;
    }
}
