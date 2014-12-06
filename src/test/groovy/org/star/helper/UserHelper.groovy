package org.star.helper

import geb.Browser
import org.star.domain.User
import org.star.page.DashBoardPage
import org.star.page.TopPage
import org.star.page.UserListPage

/**
 * Created by tamagawa on 2014/11/23.
 */
class UserHelper {

    public static User createDefaultUser(Browser browser) {
        String userName = "user_" + UUID.randomUUID()
        String password = UUID.randomUUID().toString()
        String mailAddress = "stac2014tamagawa@gmail.com"
        String mailPassword = "tamagawa2014"

        createUser(browser, userName, password, mailAddress, mailPassword)
    }

    public
    static User createUser(Browser browser, String username, String password, String mailAddress, String mailPassword) {
        TopPage topPage = browser.to TopPage
        topPage.login "admin", "admin"
        DashBoardPage dashBoardPage = browser.at DashBoardPage
        dashBoardPage.header.admin.user.click()

        UserListPage userListPage = browser.at UserListPage
        userListPage.addUser(username, password, mailAddress)
        browser.waitFor { userListPage.message.isDisplayed() }
        userListPage.header.logout.click()

        new User(userName: username, password: password, mailAddress: mailAddress, mailPassword: mailPassword, browser: browser)
    }
}