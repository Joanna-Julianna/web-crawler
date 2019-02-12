package crawler;

import org.jsoup.select.Elements;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class WebCrawler {

    private PermissionController permissionController;
    private Frontier frontier;
    private SiteLoader siteLoader;
    private AtomicInteger activeThreads = new AtomicInteger(0);

    public WebCrawler(PermissionController permissionController, Frontier frontier, SiteLoader siteLoader) {
        this.permissionController = permissionController;
        this.frontier = frontier;
        this.siteLoader = siteLoader;
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
        Elements linksOnPage = siteLoader.findLinks(url);
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
