package pl.joanna.webcrawler.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SiteLoader {

    private static final Logger LOG = LoggerFactory.getLogger(SiteLoader.class);

    /**
     * Find all links on site
     */
    public Set<String> findPageLinksOnWebsite(String url) {
        Elements linksOnPage = findLinks(url);
        return linksOnPage.stream()
                .map(page -> page.attr("abs:href"))
                .filter(link -> !link.isEmpty())
                .collect(Collectors.toSet());
    }

    private Elements findLinks(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Elements linksOnPage = document.select("a[href]");
            Elements sourcesOnPage = document.select("[src]");
            linksOnPage.addAll(sourcesOnPage);
            return linksOnPage;
        } catch (IOException e) {
            LOG.warn("For '{}': {}", url, e.getMessage());
            return new Elements(0);
        }
    }

}
