/*
 * Copyright (c) 2021. Pradeesh Kumar
 */

package org.pradeesh.crawler.core.core.support;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.pradeesh.crawler.core.core.CrawlerWebClient;
import org.pradeesh.crawler.core.message.WebDocument;
import org.springframework.stereotype.Component;

/**
 * The type Http document service.
 *
 * @author pradeesh.kumar
 */
@Component
@Slf4j
public class DefaultCrawlerWebClient implements CrawlerWebClient {

    @Override
    public WebDocument get(String url) {
        try {
            Connection.Response response = Jsoup.connect(url).execute();
            int responseStatus = response.statusCode();
            if (!is2xxResponse(responseStatus)) {
                log.warn("Received non success response for the url: {}, response code: {}, status: ", url, responseStatus, response.statusMessage());
            }
            if (!isHtmlPage(response.contentType())) {
                log.warn("Url is not a html page: {}, contentType: {}", url, response.contentType());
            }
            Document htmlDoc = response.parse();
            return (WebDocument) htmlDoc;
        } catch (Exception e) {
            log.error("", e);
            throw new RuntimeException();
        }
    }

    // TODO - check if the page is html
    private static boolean isHtmlPage(String contentType) {
        return true;
    }

    private static boolean is2xxResponse(int response) {
        return response >= 200 && response < 300;
    }

}
