import crawler.WebCrawler;
import crawler.WebCrawlerRunnable;
import robotstxt.RobotsPermissionsController;
import robotstxt.RobotsTxtParser;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final int THREADS = 10;

    public static void main(String[] args) throws InterruptedException {
        String url = "http://wiprodigital.com";

        RobotsTxtParser robotsTxtParser = new RobotsTxtParser();
        Set<String> disallowedPages = robotsTxtParser.findDisallowedPages(url);
        RobotsPermissionsController robotsPermissionsController = new RobotsPermissionsController(disallowedPages);

        WebCrawler webCrawler = new WebCrawler(robotsPermissionsController, url);
        webCrawler.getPageLinks(url);

        Optional<String> next = webCrawler.getQueue().getNext();
        ExecutorService executor = Executors.newFixedThreadPool(THREADS);
        while (next.isPresent()) {
            Runnable worker = new WebCrawlerRunnable(webCrawler, next.get());
            executor.execute(worker);
            next = webCrawler.getQueue().getNext();
            if (!next.isPresent()) {
                Thread.sleep(6000);
                next = webCrawler.getQueue().getNext();
            }
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }
}
