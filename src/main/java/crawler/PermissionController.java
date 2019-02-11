package crawler;

import robotstxt.RobotsPermissionsController;

public class PermissionController {

    private final String domain;
    private RobotsPermissionsController robotsPermissionsController;

    public PermissionController(String domain, RobotsPermissionsController robotsPermissionsController) {
        this.domain = domain;
        this.robotsPermissionsController = robotsPermissionsController;
    }

    /**
     * Check if link is allowed for robots and is not external link.
     */
    public boolean isAllowedToVisit(String url) {
        return belongsToDomain(domain, url)
                && robotsPermissionsController.isAllowed(domain, url);
    }

    private boolean belongsToDomain(String domain, String link) {
        return link.startsWith("https://" + domain) ||
                link.startsWith("http://" + domain);
    }
}
