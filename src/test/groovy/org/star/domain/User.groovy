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
    TopPage topPage
    DashBoardPage dashBoardPage

    def login() {
        topPage.login(username, password)
        dashBoardPage = topPage.browser.at DashBoardPage
    }

    def logout() {
        dashBoardPage.header.logout()
    }
}
