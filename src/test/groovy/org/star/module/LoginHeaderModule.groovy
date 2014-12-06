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
        admin {
            $("li.item-admin a").click()
            module AdminMenuModule
        }
        testCase(to: TestCasePage) { $("li.item-testCase a") }
        testTag(to: TagPage) { $("li.item-tag a") }
        logout(to: TopPage) { $("#logout a") }
    }
}
