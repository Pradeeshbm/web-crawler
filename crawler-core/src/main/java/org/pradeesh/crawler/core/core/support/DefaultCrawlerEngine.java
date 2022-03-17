/*
 * Copyright (c) 2021. Pradeesh Kumar
 */

package org.pradeesh.crawler.core.core.support;

import org.pradeesh.crawler.common.message.UrlIndex;
import org.pradeesh.crawler.common.producer.UrlEventProducer;
import org.pradeesh.crawler.common.repository.UrlIndexRepository;
import org.pradeesh.crawler.common.util.UrlPreProcessor;
import org.pradeesh.crawler.common.util.UrlUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pradeesh.crawler.core.core.CrawlerEngine;
import org.pradeesh.crawler.core.core.CrawlerWebClient;
import org.pradeesh.crawler.core.message.WebDocument;
import org.pradeesh.crawler.core.storage.StorageService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * The type Default url message processor.
 *
 * @author pradeesh.kumar
 */
@Component
@RequiredArgsConstructor
@Slf4j
public final class DefaultCrawlerEngine implements CrawlerEngine {

    private static final String HYPERLINK_TAG_NAME = "a";
    private static final String HYPERLINK_ATTR_NAME = "href";

    private final CrawlerWebClient webClient;
    private final UrlIndexRepository indexRepository;
    private final StorageService storageService;
    private final UrlEventProducer urlEventProducer;

    @Override
    public void crawl(String url) {
        log.info("Processing the url: {}", url);
        WebDocument htmlDoc = webClient.get(url);
        String documentId = storageService.store(htmlDoc);
        saveDb(documentId, url);
        processChildLinks(htmlDoc, url);
    }

    private void processChildLinks(WebDocument htmlDoc, String originUrl) {
        htmlDoc.select(HYPERLINK_TAG_NAME)
                .parallelStream()
                .map(hyperlink -> hyperlink.attr(HYPERLINK_ATTR_NAME))
                .map(UrlPreProcessor::doPreprocess)
                .filter(url -> UrlUtils.isFromSameOrigin(originUrl, url))
                .filter(this::notExistsOrExpired)
                .forEach(urlEventProducer::emit);
    }

    private void saveDb(String documentId, String url) {
        UrlIndex index = UrlIndex.builder()
                .docId(documentId)
                .url(url)
                .build();
        indexRepository.save(index);
    }

    private boolean notExistsOrExpired(String url) {
        Mono<UrlIndex> index = indexRepository.findByUrl(url);
        return index.blockOptional().isEmpty();
    }

}
