package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import robotstxt.RobotsPermissionsController;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class WebCrawler {

    private static final Logger LOG = LoggerFactory.getLogger(WebCrawler.class);

    private RobotsPermissionsController robotsPermissionsController;
    private String domain;
    private Frontier frontier;
    private AtomicInteger activeThreads = new AtomicInteger(0);

    public WebCrawler(RobotsPermissionsController robotsPermissionsController, Frontier frontier, String url) {
        this.robotsPermissionsController = robotsPermissionsController;
        this.domain = url.replaceFirst("http://", "");
        this.frontier = frontier;
        this.frontier.add(url);
    }

    public Optional<String> getNext() {
        return frontier.getNext();
    }

    public void getPageLinks(String url) {
        if (belongsToDomain(domain, url) && robotsPermissionsController.isAllowed(domain, url)) {
            Elements linksOnPage = findLinks(url);
            linksOnPage.stream()
                    .map(page -> page.attr("abs:href"))
                    .filter(link -> !link.isEmpty())
                    .forEach(link -> frontier.add(link));
        } else {
            frontier.markAsCrawled(url);
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

    private boolean belongsToDomain(String domain, String link) {
        return link.startsWith("https://" + domain) ||
                link.startsWith("http://" + domain);
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
