package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import robotstxt.RobotsPermissionsController;

import java.io.IOException;
import java.util.HashSet;

public class WebCrawler {

    private static final Logger LOG = LoggerFactory.getLogger(WebCrawler.class);

    private RobotsPermissionsController robotsPermissionsController;

    private HashSet<String> links = new HashSet<>();
    private String domain;

    public WebCrawler(RobotsPermissionsController robotsPermissionsController, String domain) {
        this.robotsPermissionsController = robotsPermissionsController;
        this.domain = domain;
    }

    public void getPageLinks(String url) {
        if (!links.contains(url)) {
            try {
                if (links.add(url)) {
                    LOG.info(url);
                }
                if (belongsToDomain(domain, url) && robotsPermissionsController.isAllowed(domain, url)) {

                    Document document = Jsoup.connect(url).get();
                    Elements linksOnPage = document.select("a[href]");
                    Elements sourcesOnPage = document.select("[src]");
                    linksOnPage.addAll(sourcesOnPage);

                    for (Element page : linksOnPage) {
                        String link = page.attr("abs:href");
                        getPageLinks(link);
                    }

                }
            } catch (IOException e) {
                LOG.warn("For '{}': {}", url, e.getMessage());
            }
        }
    }

    private boolean belongsToDomain(String domain, String link) {
        return link.startsWith("https://" + domain) ||
                link.startsWith("http://" + domain);
    }
}
