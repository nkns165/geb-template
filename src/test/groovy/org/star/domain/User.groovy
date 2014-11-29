package org.star.domain

import geb.Browser
import org.star.helper.EmailHelper
import org.star.helper.UserHelper
import org.star.page.DashBoardPage
import org.star.page.TopPage

/**
 * Created by kenichiro_ota on 14/11/23.
 */
class User {
    String username
    String password
    String mailAddress
    String mailPassword
    Browser browser

    def login() {
        browser.page.login(username, password)
        browser.at DashBoardPage
    }

    def logout() {
        browser.page.header.logout()
        browser.at TopPage
    }

    def addUser(User user) {
        browser.page.header.openMenuUser()
        browser.page.addUser(user.username, user.password, user.mailAddress)
    }

    boolean receiveRegisteredEmail() {
        EmailHelper emailHelper = new EmailHelper(mailAddress: mailAddress, mailPassword: mailPassword)
        emailHelper.containText(username, 5)
    }
}
