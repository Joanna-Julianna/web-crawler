package pl.joanna.webcrawler.permissions

import pl.joanna.webcrawler.robotstxt.RobotsPermissionsService
import pl.joanna.webcrawler.robotstxt.RobotsTxtParser
import spock.lang.Specification
import spock.lang.Unroll

class PermissionServiceTest extends Specification {

    private RobotsPermissionsService robotsPermissionsService = Mock()
    private RobotsTxtParser robotsTxtParser = Mock()
    private PermissionService permissionService

    @Unroll
    def "Check if is allowed to visit page #page"() {
        given:
        def domain = "wiprodigital.com"
        PermissionModel permissionModel = new PermissionModel(domain, new HashSet<String>())
        robotsPermissionsService.isAllowed(permissionModel, page) >> allowedToRobots
        permissionService = new PermissionService(robotsPermissionsService, robotsTxtParser)

        when:
        boolean result = permissionService.isAllowedToVisit(permissionModel, page)

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
