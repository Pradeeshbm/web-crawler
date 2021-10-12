/*
 * Copyright (c) 2021. Pradeesh Kumar
 */
package org.pradeesh.crawler.crawlerweb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pradeesh.crawler.crawlerweb.dto.IndexResponse;
import org.pradeesh.crawler.crawlerweb.event.producer.UrlEventProducer;
import org.pradeesh.crawler.crawlerweb.model.UrlIndex;
import org.pradeesh.crawler.crawlerweb.repository.UrlIndexRepository;
import org.pradeesh.crawler.crawlerweb.util.ErrorEnum;
import org.pradeesh.crawler.crawlerweb.util.UrlUtils;
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
        try {
            log.debug("Parsing the url: {}", url);
            final String refinedUrl = UrlUtils.removeHash(url);
            log.debug("After removing the hash part of the url: {}", refinedUrl);
            Mono<UrlIndex> urlIndexMono = urlIndexRepo.findByUrl(refinedUrl);
            return urlIndexMono
                    .map(urlIndex -> processUrl(urlIndex, refinedUrl))
                    .doOnError(e -> {
                        log.error("Error occurred while retrieving the url index for the url: {}", refinedUrl, e);
                        throw ErrorEnum.ADD_TO_INDEX_ERR.exception();
                    })
                    .log();
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while adding the url the index: {}", url, e);
            throw ErrorEnum.ADD_TO_INDEX_ERR.exception();
        }
    }

    private IndexResponse processUrl(UrlIndex urlIndex, String url) {
        if (urlIndex == null || isExpired(urlIndex)) {
            urlEventProducer.emit(url);
            log.info("Add to index operaiton has been successful for the url: {}", url);
            return new IndexResponse(null, LocalDateTime.now(), IndexResponse.Status.CREATED);
        } else {
            log.info("Url: {} already exists", url);
            return new IndexResponse(urlIndex.getId(), urlIndex.getLastModifiedTime(), IndexResponse.Status.EXISTS);
        }
    }

    private static boolean isExpired(UrlIndex urlIndex) {
        return false;
    }

}
