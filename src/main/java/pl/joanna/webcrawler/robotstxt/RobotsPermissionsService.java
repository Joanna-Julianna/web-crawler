package pl.joanna.webcrawler.robotstxt;

import org.springframework.stereotype.Service;
import pl.joanna.webcrawler.permissions.PermissionModel;

@Service
public class RobotsPermissionsService {

    /**
     * Check if robots.txt isAllowed pl.joanna.webcrawler.crawler to visit the page.
     */
    public boolean isAllowed(PermissionModel permissionModel, String url) {
        String page = url.replaceFirst("http://", "")
                .replaceFirst("https://", "");
        return permissionModel.getDisallowedPages().stream()
                .noneMatch(disallowedPage -> page.startsWith(permissionModel.getDomain() + disallowedPage));
    }
}
