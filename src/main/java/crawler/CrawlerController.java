package crawler;

import robotstxt.RobotsPermissionsController;
import robotstxt.RobotsTxtParser;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CrawlerController {

    public void execute(String url) throws InterruptedException {
        WebCrawler webCrawler = initWebCrawler(url);
        webCrawler.getPageLinks(url);
        Optional<String> next = webCrawler.getNext();

        int nThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        while (next.isPresent() || webCrawler.isAnyActiveThread()) {
            if (next.isPresent()) {
                Runnable worker = new WebCrawlerRunnable(webCrawler, next.get());
                webCrawler.incrementThreads();
                executor.execute(worker);
            }
            next = webCrawler.getNext();
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }

    private WebCrawler initWebCrawler(String url) {
        RobotsTxtParser robotsTxtParser = new RobotsTxtParser();
        Set<String> disallowedPages = robotsTxtParser.findDisallowedPages(url);
        RobotsPermissionsController robotsPermissionsController = new RobotsPermissionsController(disallowedPages);
        String domain = url.replaceFirst("http://", "");
        PermissionController permissionController = new PermissionController(domain, robotsPermissionsController);

        return new WebCrawler(permissionController, new Frontier(url));
    }
}
