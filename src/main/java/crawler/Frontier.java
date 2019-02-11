package crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Frontier {

    private List<String> crawledSites = new ArrayList<>();
    private List<String> notVisitedSites = new ArrayList<>();

    public Frontier(String site) {
        this.notVisitedSites.add(site);
    }

    /**
     * Add unvisited site.
     */
    public boolean add(String site) {
        synchronized (this) {
            if (!crawledSites.contains(site) && !notVisitedSites.contains(site)) {
                return notVisitedSites.add(site);
            }
        }
        return false;
    }

    /**
     * Get getNext site to crawl.
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
    public boolean markAsCrawled(String site) {
        synchronized (this) {
            if (!crawledSites.contains(site)) {
                return crawledSites.add(site);
            }
        }
        return false;
    }

}
