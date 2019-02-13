package pl.joanna.webcrawler.crawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.joanna.webcrawler.permissions.PermissionController;
import pl.joanna.webcrawler.permissions.PermissionModel;

import java.util.Optional;

@Controller
public class WebCrawler {

    private PermissionController permissionController;
    private Frontier frontier;
    private SiteLoader siteLoader;

    @Autowired
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
    public void getPageLinks(String url, PermissionModel permissionModel) {
        siteLoader.getPageLinks(url).forEach(link -> processLink(permissionModel, link));
    }

    private void processLink(PermissionModel permissionModel, String link) {
        if (permissionController.isAllowedToVisit(permissionModel, link)) {
            frontier.add(link);
        } else {
            frontier.markAsCrawled(link);
        }
    }

}
