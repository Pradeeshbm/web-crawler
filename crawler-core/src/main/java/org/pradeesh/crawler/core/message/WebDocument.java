/*
 * Copyright (c) 2021. Pradeesh Kumar
 */

package org.pradeesh.crawler.core.message;

import org.jsoup.nodes.Document;

public class WebDocument extends Document {

    private WebDocument(String baseUri) {
        super(baseUri);
    }

    public static WebDocument create(String baseUri) {
        return new WebDocument(baseUri);
    }
}
