package org.star.module

import geb.Module
import org.star.page.DashBoardPage

class NotLoginHeaderModule extends Module {
    static content = {
        name { $("input", name: "j_username") }
        password { $("input", name: "j_password") }
        login(to: DashBoardPage) {
            $("input", value: "Login")
        }
    }
}
