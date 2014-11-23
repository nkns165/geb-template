package org.star.module

import geb.Module
import org.star.page.TagListPage
import org.star.page.TestCaseListPage
import org.star.page.TopPage
import org.star.page.UserListPage

/**
 * Created by tamagawa on 2014/11/23.
 */
class LoginHeaderModule extends Module {
    static content = {
        menuAdmin { $("li.item-admin a") }
        menuUser(to: UserListPage) { $("li.item-secureUser a") }
        menuTestCaseList(to: TestCaseListPage){ $("li.item-testCase a") }
        menuTagList(to: TagListPage){ $("li.item-tag a") }
        buttonLogout(to: TopPage) { $("#logout a") }
    }

    public void openMenuUser() {
        menuAdmin.click()
        waitFor { menuUser.isDisplayed() }
        menuUser.click()
    }

    public void openMenuTestCaseList(){
        menuTestCaseList.click()
    }

    public void openMenuTagList(){
        menuTagList.click()
    }

    public void logout() {
        buttonLogout.click()
    }
}
