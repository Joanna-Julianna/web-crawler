package pl.joanna.webcrawler.crawler


import spock.lang.Specification

class SiteLoaderTest extends Specification {

    private SiteLoader siteLoader = new SiteLoader()

    def "find page links on website"() {
        given:
        String url = "http://wiprodigital.com"

        when:
        Set<String> links = siteLoader.findPageLinksOnWebsite(url)

        then:
        !links.isEmpty()
    }
}
