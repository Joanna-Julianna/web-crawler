package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import robotstxt.RobotsPermissionsController;

import java.io.IOException;

public class WebCrawler {

    private static final Logger LOG = LoggerFactory.getLogger(WebCrawler.class);

    private RobotsPermissionsController robotsPermissionsController;
    private String domain;
    private Queue queue = new Queue();

    public WebCrawler(RobotsPermissionsController robotsPermissionsController, String url) {
        this.robotsPermissionsController = robotsPermissionsController;
        this.domain = url.replaceFirst("http://", "");
        this.queue.add(url);
    }

    public Queue getQueue() {
        return queue;
    }

    public void getPageLinks(String url) {
        if (belongsToDomain(domain, url) && robotsPermissionsController.isAllowed(domain, url)) {
            Elements linksOnPage = findLinks(url);
            for (Element page : linksOnPage) {
                String link = page.attr("abs:href");
                if (!link.isEmpty() && queue.add(link)) {
                    LOG.info(link);
                }
            }
        } else {
            queue.markAsCrawled(url);
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
}
