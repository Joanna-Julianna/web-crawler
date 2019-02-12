package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class SiteLoader {

    private static final Logger LOG = LoggerFactory.getLogger(SiteLoader.class);

    /**
     * Find all inks on site
     */
    public Set<String> getPageLinks(String url) {
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
