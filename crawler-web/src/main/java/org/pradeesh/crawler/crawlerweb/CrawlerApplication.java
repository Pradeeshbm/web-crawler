/*
 * Copyright (c) 2021. Pradeesh Kumar
 */
package org.pradeesh.crawler.crawlerweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * The type Crawler application.
 *
 * @author pradeesh.kumar
 */
@SpringBootApplication
@ConfigurationPropertiesScan("org.pradeesh.crawler.crawlerweb.config")
public class CrawlerApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(CrawlerApplication.class, args);
    }
}
