package pl.joanna.webcrawler.crawler

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class CrawlerServiceTest extends Specification {

    @Autowired
    private CrawlerService crawlerService

    def "find all sites"() {
        given:
        String url = "http://wiprodigital.com"

        when:
        Set<String> sites = crawlerService.findAllSites(url)

        then:
        !sites.isEmpty()
        sites.each { site -> println "${site}" }
    }
}
