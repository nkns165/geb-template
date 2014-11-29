package org.star.module

import geb.Module
import org.star.page.TagPage
import org.star.page.TestCasePage
import org.star.page.TopPage
import org.star.page.UserListPage

/**
 * Created by tamagawa on 2014/11/23.
 */
class LoginHeaderModule extends Module {
    static content = {
        menuAdmin { $("li.item-admin a") }
        menuUser(to: UserListPage) { $("li.item-secureUser a") }
        menuTestCaseList(to: TestCasePage) { $("li.item-testCase a") }
        menuTagList(to: TagPage) { $("li.item-tag a") }
        buttonLogout(to: TopPage) { $("#logout a") }
    }

    public void openMenuUser() {
        menuAdmin.click()
        waitFor { menuUser.isDisplayed() }
        menuUser.click()
    }

    public void openMenuTestCase() {
        menuTestCaseList.click()
    }

    public void openMenuTag() {
        menuTagList.click()
    }

    public void logout() {
        buttonLogout.click()
    }
}
