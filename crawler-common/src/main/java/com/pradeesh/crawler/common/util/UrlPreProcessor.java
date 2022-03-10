package com.pradeesh.crawler.common.util;

public class UrlPreProcessor {

    private static final char CHAR_HASH = '#';

    public static String doPreprocess(String url) {
        return removeHash(url);
    }

    /**
     * Removes the hash part of the url and returns the rest of the url
     *
     * @return the string
     */
    private static String removeHash(String url) {
        int hashIndex = url.indexOf(CHAR_HASH);
        if (hashIndex != -1) {
            url = url.substring(0, hashIndex - 1);
        }
        return url;
    }
}
