package pl.joanna.webcrawler.permissions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.joanna.webcrawler.robotstxt.RobotsPermissionsController;
import pl.joanna.webcrawler.robotstxt.RobotsTxtParser;

import java.util.Set;

@Controller
public class PermissionController {

    private RobotsTxtParser robotsTxtParser;
    private RobotsPermissionsController robotsPermissionsController;

    @Autowired
    public PermissionController(RobotsPermissionsController robotsPermissionsController, RobotsTxtParser robotsTxtParser) {
        this.robotsPermissionsController = robotsPermissionsController;
        this.robotsTxtParser = robotsTxtParser;
    }

    /**
     * Init permissions from robots.txt
     */
    public PermissionModel init(String url) {
        Set<String> disallowedPages = robotsTxtParser.findDisallowedPages(url);
        String domain = url.replaceFirst("http://", "");
        return new PermissionModel(domain, disallowedPages);
    }

    /**
     * Check if link is allowed for robots and is not external link.
     */
    public boolean isAllowedToVisit(PermissionModel permissionModel, String url) {
        return belongsToDomain(permissionModel.getDomain(), url)
                && robotsPermissionsController.isAllowed(permissionModel, url);
    }

    private boolean belongsToDomain(String domain, String link) {
        return link.startsWith("https://" + domain) ||
                link.startsWith("http://" + domain);
    }
}
