package pl.joanna.webcrawler.permissions;

import java.util.Set;

public class PermissionModel {

    private final String domain;

    private final Set<String> disallowedPages;

    public PermissionModel(String domain, Set<String> disallowedPages) {
        this.domain = domain;
        this.disallowedPages = disallowedPages;
    }

    public String getDomain() {
        return domain;
    }

    public Set<String> getDisallowedPages() {
        return disallowedPages;
    }
}
