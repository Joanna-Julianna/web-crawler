package pl.joanna.webcrawler.permissions;

import org.springframework.stereotype.Component;
import pl.joanna.webcrawler.robotstxt.RobotsPermissionsService;
import pl.joanna.webcrawler.robotstxt.RobotsTxtParser;

import java.util.Set;

@Component
public class PermissionService {

    private final RobotsTxtParser robotsTxtParser;
    private final RobotsPermissionsService robotsPermissionsService;

    public PermissionService(RobotsTxtParser robotsTxtParser, RobotsPermissionsService robotsPermissionsService) {
        this.robotsTxtParser = robotsTxtParser;
        this.robotsPermissionsService = robotsPermissionsService;
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
                && robotsPermissionsService.isAllowed(permissionModel, url);
    }

    private boolean belongsToDomain(String domain, String link) {
        return link.startsWith("https://" + domain) ||
                link.startsWith("http://" + domain);
    }
}
