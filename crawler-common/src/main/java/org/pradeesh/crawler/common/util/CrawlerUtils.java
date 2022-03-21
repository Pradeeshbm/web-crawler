/*
 * Copyright (c) 2021. Pradeesh Kumar
 */

package org.pradeesh.crawler.common.util;

import org.pradeesh.crawler.common.message.UrlIndex;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * The type Crawler utils contains static utility methods.
 *
 * @author pradeesh.kumar
 */
public final class CrawlerUtils {

    private CrawlerUtils() {}

    public static boolean isExpired(UrlIndex index) {
        long days = ChronoUnit.DAYS.between(LocalDateTime.now(), index.getLastModifiedTime());
        return days >= 10;
    }
}
