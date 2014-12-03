package org.star.domain

import geb.Browser
import org.star.helper.EmailHelper

/**
 * Created by kenichiro_ota on 14/11/23.
 */
class User {
    String userName
    String password
    String mailAddress
    String mailPassword
    Browser browser

    def login() {
        browser.page.login(userName, password)
    }

    def logout() {
        browser.page.header.logout()
        true
    }

    boolean receiveRegisteredEmail() {
        EmailHelper emailHelper = new EmailHelper(mailAddress: mailAddress, mailPassword: mailPassword)
        emailHelper.containText(userName, 5)
    }
}
