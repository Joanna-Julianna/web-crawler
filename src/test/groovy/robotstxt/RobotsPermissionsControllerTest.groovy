package robotstxt

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class RobotsPermissionsControllerTest extends Specification {

    def "check if #page is allowed"() {
        given:
        def disallowedPages = Set.of("/wp-admin/", "/wp-test/")
        def controller = new RobotsPermissionsController(disallowedPages)

        when:
        def result = controller.isAllowed("wiprodigital.com", page)

        then:
        result == allowed

        where:
        page                                     | allowed
        "https://wiprodigital.com/wp-admin/"     | false
        "http://wiprodigital.com/wp-admin/"      | false
        "https://wiprodigital.com/wp-admin/test" | false
        "https://wiprodigital.com/"              | true
        "https://wiprodigital.com/wp-abc"        | true
    }
}
