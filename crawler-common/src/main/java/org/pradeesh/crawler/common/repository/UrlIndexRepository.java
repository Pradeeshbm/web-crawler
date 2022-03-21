/*
 * Copyright (c) 2021. Pradeesh Kumar
 */
package org.pradeesh.crawler.common.repository;

import org.pradeesh.crawler.common.message.UrlIndex;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * The interface Url index repository to perform CRUD on Url Index database
 *
 * @author pradeesh.kumar
 */
@Repository
public interface UrlIndexRepository extends ReactiveCouchbaseRepository<UrlIndex, String> {

    /**
     * Returns the UrlIndex from database for the specified url
     *
     * @param url the url
     * @return the mono
     */
    Mono<UrlIndex> findByUrl(String url);
}
