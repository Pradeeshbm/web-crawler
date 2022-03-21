/*
 * Copyright (c) 2021. Pradeesh Kumar
 */
package org.pradeesh.crawler.web.service;

import org.pradeesh.crawler.common.error.HttpErrorEnum;
import org.pradeesh.crawler.common.message.UrlIndex;
import org.pradeesh.crawler.common.producer.UrlEventProducer;
import org.pradeesh.crawler.common.repository.UrlIndexRepository;
import org.pradeesh.crawler.common.util.CrawlerUtils;
import org.pradeesh.crawler.common.util.UrlPreProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pradeesh.crawler.web.dto.IndexResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * The type Default url indexer service.
 *
 * @author pradeesh.kumar
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultUrlIndexerService implements UrlIndexerService {

    private final UrlIndexRepository urlIndexRepo;
    private final UrlEventProducer urlEventProducer;

    @Override
    public Mono<IndexResponse> addToIndex(final String url) {
        log.debug("Parsing the url: {}", url);
        final String refinedUrl = UrlPreProcessor.doPreprocess(url);
        log.debug("After removing the hash part of the url: {}", refinedUrl);
        Mono<UrlIndex> urlIndexMono = urlIndexRepo.findByUrl(refinedUrl);
        return urlIndexMono.map(urlIndex -> existsOrCreate(urlIndex, refinedUrl))
                .or(Mono.just(create(url))).onErrorReturn(IndexResponse.errorResponse())
                .doOnError(e -> {
            log.error("Error occurred while retrieving the url index for the url: {}", refinedUrl, e);
            throw HttpErrorEnum.ADD_TO_INDEX_ERR.exception();
        }).log();
    }

    private IndexResponse create(String url) {
        urlEventProducer.emit(url);
        log.info("Add to index operation has been successful for the url: {}", url);
        return new IndexResponse(null, LocalDateTime.now(), IndexResponse.Status.CREATED);
    }

    private IndexResponse existsOrCreate(UrlIndex urlIndex, String url) {
        if (CrawlerUtils.isExpired(urlIndex)) {
            return create(url);
        } else {
            log.info("Url: {} already exists", url);
            return new IndexResponse(urlIndex.getId(), urlIndex.getLastModifiedTime(), IndexResponse.Status.EXISTS);
        }
    }

}
