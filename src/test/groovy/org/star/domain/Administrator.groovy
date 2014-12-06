package org.star.domain

/**
 * Created by kenichiro_ota on 14/11/30.
 */
class Administrator extends User {
    def addUser(User user) {
        browser.page.header.admin.user.click()
        browser.page.createUser(user.userName, user.password, user.mailAddress)
    }
}
