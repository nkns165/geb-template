package org.star.module

import geb.Module
import org.star.page.TagPage
import org.star.page.TestCasePage
import org.star.page.TopPage
import org.star.page.UserListPage

/**
 * Created by itagakishintarou on 2014/12/06.
 */
class AdminMenuModule extends Module {
    static content = {
        user(to: UserListPage, toWait: true) { $("li.item-secureUser a") }
    }
}
