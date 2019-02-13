package pl.joanna.webcrawler.crawler

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class FrontierTest extends Specification {

    private Frontier frontier = new Frontier()

    def "Add site"() {
        when:
        String site = "https://wiprodigital.com/abc"
        boolean firstTime = frontier.add(site)
        boolean secondTime = frontier.add(site)

        then:
        firstTime
        !secondTime
    }

    def "get next for given one not visited element"() {
        given:
        String site = "https://wiprodigital.com/abc"
        frontier.add(site)

        when:
        Optional<String> firstElement = frontier.getNext()
        Optional<String> next = frontier.getNext()

        then:
        firstElement.isPresent()
        !next.isPresent()
    }

    def "mark as crawled"() {
        when:
        String site = "https://wiprodigital.com/abc"
        boolean firstOccurrence = frontier.markAsCrawled(site)
        boolean secondOccurrence = frontier.markAsCrawled(site)

        then:
        firstOccurrence
        !secondOccurrence
    }
}
