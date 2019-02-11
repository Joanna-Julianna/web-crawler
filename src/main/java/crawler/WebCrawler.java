package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class WebCrawler {

    private static final Logger LOG = LoggerFactory.getLogger(WebCrawler.class);

    private PermissionController permissionController;
    private Frontier frontier;
    private AtomicInteger activeThreads = new AtomicInteger(0);

    public WebCrawler(PermissionController permissionController, Frontier frontier) {
        this.permissionController = permissionController;
        this.frontier = frontier;
    }

    /**
     * Get next site url from frontier.
     */
    public Optional<String> getNext() {
        return frontier.getNext();
    }

    /**
     * Load page and find all links on page.
     */
    public void getPageLinks(String url) {
        Elements linksOnPage = findLinks(url);
        linksOnPage.stream()
                .map(page -> page.attr("abs:href"))
                .filter(link -> !link.isEmpty())
                .forEach(this::processLink);
    }

    private void processLink(String link) {
        if (permissionController.isAllowedToVisit(link)) {
            frontier.add(link);
        } else {
            frontier.markAsCrawled(link);
        }
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

    public boolean isAnyActiveThread() {
        return activeThreads.get() > 0;
    }

    public void incrementThreads() {
        activeThreads.incrementAndGet();
    }

    public void decrementThreads() {
        activeThreads.decrementAndGet();
    }

}
