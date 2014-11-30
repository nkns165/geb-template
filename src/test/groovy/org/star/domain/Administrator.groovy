package org.star.domain

/**
 * Created by kenichiro_ota on 14/11/30.
 */
class Administrator extends User {
    def addUser(User user) {
        browser.page.header.openMenuUser()
        browser.page.addUser(user.username, user.password, user.mailAddress)
    }
}
