package org.star.helper

import geb.Browser
import geb.Page
import org.openqa.selenium.WebDriver
import org.star.page.DashBoardPage
import org.star.page.TopPage
import org.star.page.UserListPage

/**
 * Created by tamagawa on 2014/11/23.
 */
class UserHelper {

    public static void createUser(Browser browser) {
        def userName = "user_" + UUID.randomUUID()
        def password = UUID.randomUUID().toString()
        def mailAddress = "hiroko.tamagawa@shiftinc.jp"

        TopPage topPage = browser.to TopPage
        topPage.login "admin", "admin"
        DashBoardPage dashBoardPage = browser.at DashBoardPage
        dashBoardPage.header.openMenuUser()

        UserListPage userListPage = browser.at UserListPage
        userListPage.addUser(userName, password, mailAddress)
        browser.waitFor { userListPage.message.isDisplayed() }
        userListPage.header.logout()

        topPage = browser.at TopPage
        topPage.login userName, password

        browser.at DashBoardPage
    }
}
