package pl.joanna.webcrawler.robotstxt

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class RobotsTxtParserTest extends Specification {

    /**
     * You can ignore the warnings.
     * https://issues.apache.org/jira/browse/GROOVY-8339
     */
    def "disallowed pages for #page"() {
        given:
        def inputText = new File("src/test/resources/robotstxt/" + page + ".txt").readLines().join("\n")
        def robotsTxtParser = new RobotsTxtParser()

        when:
        def result = robotsTxtParser.parse(inputText)

        then:
        result.containsAll(disallowedPages)

        where:
        page           | disallowedPages
        "youtube"      | ["/channel/*/community", "/comment", "/get_video"]
        "wiprodigital" | ["/wp-admin/"]
    }

}
