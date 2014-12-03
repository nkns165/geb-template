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
        admin { $("li.item-admin a") }
        user(to: UserListPage) { $("li.item-secureUser a") }
        testCaseList(to: TestCasePage) { $("li.item-testCase a") }
        tagList(to: TagPage) { $("li.item-tag a") }
        logout(to: TopPage) { $("#logout a") }
    }

    public void openMenuUser() {
        admin.click()
        waitFor { user.isDisplayed() }
        user.click()
    }

    public void openMenuTestCase() {
        testCaseList.click()
    }

    public void openMenuTag() {
        tagList.click()
    }

    public void logout() {
        logout.click()
    }
}
