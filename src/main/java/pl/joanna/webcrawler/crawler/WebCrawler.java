package pl.joanna.webcrawler.crawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.joanna.webcrawler.permissions.PermissionService;
import pl.joanna.webcrawler.permissions.PermissionModel;

import java.util.Optional;
import java.util.Set;

@Service
public class WebCrawler {

    private PermissionService permissionService;
    private Frontier frontier;
    private SiteLoader siteLoader;

    @Autowired
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
        if (permissionService.isAllowedToVisit(permissionModel, link)) {
            frontier.add(link);
        } else {
            frontier.markAsCrawled(link);
        }
    }

    public Set<String> getCrawledSites() {
        return frontier.getCrawledSites();
    }

}
