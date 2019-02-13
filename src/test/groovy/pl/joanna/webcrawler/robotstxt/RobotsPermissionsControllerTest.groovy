package pl.joanna.webcrawler.robotstxt

import pl.joanna.webcrawler.permissions.PermissionModel
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class RobotsPermissionsControllerTest extends Specification {

    def "check if #page is allowed"() {
        given:
        def domain = "wiprodigital.com"
        def disallowedPages = Set.of("/wp-admin/", "/wp-test/")
        PermissionModel permissionModel = new PermissionModel(domain, disallowedPages)
        RobotsPermissionsController controller = new RobotsPermissionsController()

        when:
        def result = controller.isAllowed(permissionModel, page)

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
