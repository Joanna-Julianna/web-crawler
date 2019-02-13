package pl.joanna.webcrawler.crawler


import spock.lang.Specification

class SiteLoaderTest extends Specification {

    private SiteLoader siteLoader = new SiteLoader()

    def "get page links"() {
        given:
        String url = "http://wiprodigital.com"

        when:
        Set<String> links = siteLoader.getPageLinks(url)

        then:
        !links.isEmpty()
    }
}
