package pl.joanna.webcrawler.crawler

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class CrawlerControllerTest extends Specification {

    @Autowired
    private CrawlerController crawlerController

    def "Execute"() {
        given:
        String url = "http://wiprodigital.com"

        when:
        crawlerController.execute(url)

        then:
        1 == 1//todoasia
    }
}
