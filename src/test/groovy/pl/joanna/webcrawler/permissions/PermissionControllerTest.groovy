package pl.joanna.webcrawler.permissions

import pl.joanna.webcrawler.robotstxt.RobotsPermissionsController
import pl.joanna.webcrawler.robotstxt.RobotsTxtParser
import spock.lang.Specification
import spock.lang.Unroll

class PermissionControllerTest extends Specification {

    private RobotsPermissionsController robotsPermissionsController = Mock()
    private RobotsTxtParser robotsTxtParser = Mock()
    private PermissionController permissionController

    @Unroll
    def "Check if is allowed to visit page #page"() {
        given:
        def domain = "wiprodigital.com"
        PermissionModel permissionModel = new PermissionModel(domain, new HashSet<String>())
        robotsPermissionsController.isAllowed(permissionModel, page) >> allowedToRobots
        permissionController = new PermissionController(robotsPermissionsController, robotsTxtParser)

        when:
        boolean result = permissionController.isAllowedToVisit(permissionModel, page)

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
