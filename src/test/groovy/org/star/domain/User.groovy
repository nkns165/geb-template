package org.star.domain

import geb.Browser
import org.star.page.DashBoardPage
import org.star.page.TopPage

/**
 * Created by kenichiro_ota on 14/11/23.
 */
class User {
    String username
    String password
    String mailAddress
    Browser browser

    def login() {
        browser.page.login(username, password)
    }

    def logout() {
        browser.page.header.logout()
    }
}
