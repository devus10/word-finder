package com.pakisoft.wordfinder.infrastructure.dictionary.polish.sjp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HtmlDocumentFetcher {

    Document getDocument(String url) throws IOException {
        return Jsoup.connect(url).get();
    }
}
