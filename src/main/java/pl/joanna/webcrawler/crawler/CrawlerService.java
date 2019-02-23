package pl.joanna.webcrawler.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.joanna.webcrawler.permissions.PermissionModel;
import pl.joanna.webcrawler.permissions.PermissionService;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CrawlerService {

    private static final Logger LOG = LoggerFactory.getLogger(CrawlerService.class);

    private final PermissionService permissionService;
    private final WebCrawler webCrawler;

    public CrawlerService(PermissionService permissionService, WebCrawler webCrawler) {
        this.permissionService = permissionService;
        this.webCrawler = webCrawler;
    }

    /**
     * Execute crawler
     *
     * @param url first page to visit
     * @return crawled sites
     */
    public Set<String> findAllSites(String url) {
        PermissionModel permissionModel = permissionService.init(url);
        Optional<String> next = Optional.of(url);
        AtomicInteger activeThreads = new AtomicInteger(0);
        int nThreads = Runtime.getRuntime().availableProcessors();

        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        do {
            if (next.isPresent()) {
                String link = next.get();
                activeThreads.incrementAndGet();
                executor.execute(() -> {
                            webCrawler.findPageLinks(link, permissionModel);
                            activeThreads.decrementAndGet();
                        }
                );
            } else {
                waitForThread();
            }
            next = webCrawler.getNext();
        } while ((next.isPresent() || activeThreads.get() > 0));
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            LOG.error("Error executor termination: {}" + e.getMessage());
        }

        return webCrawler.getCrawledSites();
    }

    /**
     * Wait for thread.
     * Thread can find new links to follow, so even if unvisited sites are empty in this moment,
     * a thread can find unvisited sites.
     */
    private void waitForThread() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            LOG.error("Wait for other tread reading links " + e.getMessage());
        }
    }

}
