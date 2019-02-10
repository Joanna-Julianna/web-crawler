import crawler.WebCrawler;
import robotstxt.RobotsPermissionsController;
import robotstxt.RobotsTxtParser;

import java.util.Set;

public class Main {

    public static void main(String[] args) {
        String domain = "wiprodigital.com";
        String url = "http://" + domain;

        RobotsTxtParser robotsTxtParser = new RobotsTxtParser();
        Set<String> disallowedPages = robotsTxtParser.findDisallowedPages(url);
        RobotsPermissionsController robotsPermissionsController = new RobotsPermissionsController(disallowedPages);

        WebCrawler webCrawler = new WebCrawler(robotsPermissionsController, domain);
        webCrawler.getPageLinks(url);
    }
}
