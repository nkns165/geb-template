package org.star.page

import geb.Page
import org.star.module.LoginHeaderModule

class DashBoardPage extends Page {
    static url = "home"
    static at = {
        $("h1").text() == "Dash Board"
    }

    static content = {
        header { module LoginHeaderModule }
    }
}
