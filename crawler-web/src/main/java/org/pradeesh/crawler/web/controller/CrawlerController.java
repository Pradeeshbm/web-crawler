/*
 * Copyright (c) 2021. Pradeesh Kumar
 */
package org.pradeesh.crawler.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.URL;
import org.pradeesh.crawler.web.dto.IndexResponse;
import org.pradeesh.crawler.web.service.UrlIndexerService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * The type Crawler controller accepts url from the query parameter and adds to the index.
 *
 * @author pradeesh.kumar
 */
@RestController
@RequestMapping("/api/v1/crawler")
@RequiredArgsConstructor
@Validated
@Slf4j
public class CrawlerController {

    private final UrlIndexerService urlIndexerService;

    /**
     * Add the input url to the url index
     *
     * @param url the url
     * @return the response entity
     */
    @PostMapping("/index")
    @ResponseStatus(HttpStatus.OK)
    public Mono<IndexResponse> addToIndex(@RequestParam @Valid
                                          @NotBlank(message = "URL Cannot be blank!")
                                          @URL(message = "Invalid URL!") String url) {
        log.info("Received addToIndex request. URL: {}", url);
        return urlIndexerService.addToIndex(url);
    }
}
