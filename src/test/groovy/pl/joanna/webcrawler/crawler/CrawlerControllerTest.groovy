package pl.joanna.webcrawler.crawler

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class CrawlerControllerTest extends Specification {

    @Autowired
    private CrawlerController crawlerController

    def "find all sites"() {
        given:
        String url = "http://wiprodigital.com"

        when:
        Set<String> sites = crawlerController.findAllSites(url)

        then:
        !sites.isEmpty()
        sites.each { site -> println "${site}" }
    }
}
