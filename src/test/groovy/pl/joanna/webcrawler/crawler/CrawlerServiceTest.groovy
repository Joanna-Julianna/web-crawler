package pl.joanna.webcrawler.crawler


import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class CrawlerServiceTest extends Specification {


//todo: add profile for integration tests
//    @Autowired
//    private CrawlerService crawlerService
//
//    def "find all sites"() {
//        given:
//        String url = "http://wiprodigital.com"
//
//        when:
//        Set<String> sites = crawlerService.findAllSites(url)
//
//        then:
//        !sites.isEmpty()
//        sites.each { site -> println "${site}" }
//    }
}
