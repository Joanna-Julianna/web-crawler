package robotstxt;

import java.util.Set;

public class RobotsPermissionsController {

    private final Set<String> disallowedPages;

    public RobotsPermissionsController(Set<String> disallowedPages) {
        this.disallowedPages = disallowedPages;
    }

    /**
     * Check if robots.txt isAllowed crawler to visit the page.
     */
    public boolean isAllowed(String domain, String url) {
        String page = url.replaceFirst("http://", "")
                .replaceFirst("https://", "");
        for (String disallowedPage : disallowedPages) {
            if (page.startsWith(domain + disallowedPage)) {
                return false;
            }
        }
        return true;
    }
}
