package crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Frontier {

    private List<String> crawledSites = new ArrayList<>();
    private List<String> notVisitedSites = new ArrayList<>();

    /**
     * Add unvisited site.
     */
    public boolean add(String site) {
        synchronized (this) {
            if (!crawledSites.contains(site) && !notVisitedSites.contains(site)) {
                System.out.println(site);
                return notVisitedSites.add(site);
            }
        }
        return false;
    }

    /**
     * Get getNext site to crawl.
     * Can return null (if nothing to crawl)
     */
    public Optional<String> getNext() {
        synchronized (this) {
            if (!notVisitedSites.isEmpty()) {
                String site = notVisitedSites.get(0);
                notVisitedSites.remove(0);
                crawledSites.add(site);
                return Optional.of(site);
            }
        }
        return Optional.empty();
    }

    /**
     * Mark site as crawled if external site or link to static content.
     */
    public void markAsCrawled(String site) {
        synchronized (this) {
            crawledSites.add(site);
            notVisitedSites.remove(site);
        }
    }

}
