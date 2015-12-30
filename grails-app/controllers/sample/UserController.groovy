package sample

import sample.Role
import sample.UserRole 

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Secured(['permitAll'])
@Transactional(readOnly = true)
class UserController {
    SpringSecurityService springSecurityService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def changeStatus(User userInstance) {
        def roleUser = Role.findByAuthority('ROLE_USER')
        def roleNonUser = Role.findByAuthority('ROLE_NONUSER')

        UserRole.remove userInstance, roleNonUser, true
        UserRole.create userInstance, roleUser, true

        render status: OK
    }

    // def getAuthority(User userInstance) {
    //     def roles = User.get(userInstance).getAuthorities()
    //     respond roles.authority, [status: OK]
    // }

    // @Secured(['ROLE_ADMIN'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def role = Role.findByAuthority('ROLE_NONUSER')
        def users = UserRole.findAllByRole(role).user

        def roles = springSecurityService.getPrincipal().getAuthorities()
        //to get role of a user
        // def roles = User.get(3).getAuthorities()

        respond User.list(params), [status: OK]
    }

    // @Secured(['permitAll'])
    @Transactional
    def save(User userInstance) {
        if (userInstance == null) {
            render status: NOT_FOUND
            return
        }

        userInstance.validate()
        if (userInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        userInstance.save flush:true
        def role = Role.findByAuthority('ROLE_NONUSER')
        UserRole.create userInstance, role, true

        respond userInstance, [status: CREATED]
    }

    @Transactional
    def update(User userInstance) {
        if (userInstance == null) {
            render status: NOT_FOUND
            return
        }

        userInstance.validate()
        if (userInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        userInstance.save flush:true
        respond userInstance, [status: OK]
    }

    @Transactional
    def delete(User userInstance) {

        if (userInstance == null) {
            render status: NOT_FOUND
            return
        }

        userInstance.delete flush:true
        render status: NO_CONTENT
    }
}
