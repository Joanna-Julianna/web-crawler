package pl.joanna.webcrawler.crawler


import pl.joanna.webcrawler.permissions.PermissionController
import pl.joanna.webcrawler.permissions.PermissionModel
import spock.lang.Specification

class WebCrawlerTest extends Specification {

    private PermissionController permissionController = Mock()
    private Frontier frontier = Mock()
    private SiteLoader siteLoader = Mock()
    private WebCrawler webCrawler = new WebCrawler(permissionController, frontier, siteLoader)

    def "find page links if site doesn't contain any link"() {
        given:
        String url = "https://wiprodigital.com/abc"
        PermissionModel permissionModel = new PermissionModel("wiprodigital.com", new HashSet<String>())
        siteLoader.findPageLinksOnWebsite(url) >> new HashSet<String>()

        when:
        webCrawler.findPageLinks(url, permissionModel)

        then:
        0 * frontier._
    }

    def "find page links if site contains link allowed to visit"() {
        given:
        String url = "https://wiprodigital.com/"
        String foundedUrl = "https://wiprodigital.com/abc"
        PermissionModel permissionModel = new PermissionModel("wiprodigital.com", new HashSet<String>())
        siteLoader.findPageLinksOnWebsite(url) >> Set.of(foundedUrl)
        permissionController.isAllowedToVisit(permissionModel, foundedUrl) >> true

        when:
        webCrawler.findPageLinks(url, permissionModel)

        then:
        1 * frontier.add(foundedUrl)
    }

    def "find page links if site contains link unallowed to visit"() {
        given:
        String url = "https://wiprodigital.com/"
        String foundedUrl = "https://wiprodigital.com/abc"
        PermissionModel permissionModel = new PermissionModel("wiprodigital.com", new HashSet<String>())
        siteLoader.findPageLinksOnWebsite(url) >> Set.of(foundedUrl)
        permissionController.isAllowedToVisit(permissionModel, foundedUrl) >> false

        when:
        webCrawler.findPageLinks(url, permissionModel)

        then:
        1 * frontier.markAsCrawled(foundedUrl)
    }
}
