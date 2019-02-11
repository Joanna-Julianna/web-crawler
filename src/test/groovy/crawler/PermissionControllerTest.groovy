package crawler

import robotstxt.RobotsPermissionsController
import spock.lang.Specification
import spock.lang.Unroll

class PermissionControllerTest extends Specification {

    private RobotsPermissionsController robotsPermissionsController = Mock()
    private PermissionController permissionController

    @Unroll
    def "Check if is allowed to visit page #page"() {
        given:
        def domain = "wiprodigital.com"
        robotsPermissionsController.isAllowed(domain, page) >> allowedToRobots
        permissionController = new PermissionController(domain, robotsPermissionsController)

        when:
        boolean result = permissionController.isAllowedToVisit(page)

        then:
        result == allowed

        where:
        page                          | allowedToRobots | allowed
        "http://wiprodigital.com/abc" | true            | true
        "http://wiprodigital.com/"    | true            | true
        "http://facebook/"            | true            | false
        "http://wiprodigital.com/abc" | false           | false
    }
}
