package pl.joanna.webcrawler.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.joanna.webcrawler.permissions.PermissionModel;
import pl.joanna.webcrawler.permissions.PermissionService;

import java.util.Optional;
import java.util.Set;

@Component
public class WebCrawler {

    private static final Logger LOG = LoggerFactory.getLogger(WebCrawler.class);

    private final PermissionService permissionService;
    private final Frontier frontier;
    private final SiteLoader siteLoader;

    public WebCrawler(PermissionService permissionService, Frontier frontier, SiteLoader siteLoader) {
        this.permissionService = permissionService;
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
    public void findPageLinks(String url, PermissionModel permissionModel) {
        siteLoader.findPageLinksOnWebsite(url)
                .forEach(link -> processLink(permissionModel, link));
    }

    private void processLink(PermissionModel permissionModel, String link) {
        boolean added;
        if (permissionService.isAllowedToVisit(permissionModel, link)) {
            added = frontier.add(link);
        } else {
            added = frontier.markAsCrawled(link);
        }

        if (added) {
            LOG.info(link);
        }
    }

    public Set<String> getCrawledSites() {
        return frontier.getCrawledSites();
    }

}
