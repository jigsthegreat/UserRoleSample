import sample.Role
import sample.User
import sample.UserRole 

class BootStrap {

    def init = { servletContext ->

    	def userRole = new Role(authority: 'ROLE_ADMIN').save(flush: true)
    	def userRole2 = new Role(authority: 'ROLE_USER').save(flush: true)
    	def userRole3 = new Role(authority: 'ROLE_NONUSER').save(flush: true)

        def testUser = new User(username: 'jigs', password: '1234')
        def testUser2 = new User(username: 'jigs2', password: '1234')
        def testUser3 = new User(username: 'jigs3', password: '1234')
        testUser.save(flush: true)
        testUser2.save(flush: true)
        testUser3.save(flush: true)

        def role = Role.findByAuthority('ROLE_ADMIN')
        def role2 = Role.findByAuthority('ROLE_USER')
        def role3 = Role.findByAuthority('ROLE_NONUSER')

        UserRole.create testUser, role, true
        UserRole.create testUser2, role2, true
        UserRole.create testUser3, role3, true

        //change NONUSER role to USER role
        // UserRole.remove testUser3, role3, true
        // UserRole.create testUser3, role2, true

        assert User.count() == 3
        assert Role.count() == 3
        assert UserRole.count() == 3

    }
    def destroy = {
    }
}
