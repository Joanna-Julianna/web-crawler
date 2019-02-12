package crawler

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class FrontierTest extends Specification {

    private static final FIRST_SITE = "https://wiprodigital.com/"
    private Frontier frontier = new Frontier(FIRST_SITE)

    def "Add #site"() {
        when:
        boolean result = frontier.add(site)

        then:
        result == added

        where:
        site                               | added
        FIRST_SITE                         | false
        "https://wiprodigital.com/newSite" | true
    }

    def "get next for given one not visited element"() {
        when:
        Optional<String> firstElement = frontier.getNext()
        Optional<String> next = frontier.getNext()

        then:
        firstElement.isPresent()
        !next.isPresent()
    }

    def "mark as crawled"() {
        when:
        boolean firstOccurrence = frontier.markAsCrawled(FIRST_SITE)
        boolean secondOccurrence = frontier.markAsCrawled(FIRST_SITE)

        then:
        firstOccurrence
        !secondOccurrence
    }
}
