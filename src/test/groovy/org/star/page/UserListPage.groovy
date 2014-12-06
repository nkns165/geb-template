package org.star.page

import geb.Page
import org.star.module.LoginHeaderModule

class UserListPage extends Page {
    static url = "secureUser"
    static at = {
        $("h1").text().contains("Secure User")
    }

    static content = {
        createUser { $("form[role=form]") }
        checkEnabled { $("input[name=enabled]") }
        message { $("div.alert") }
        header { module LoginHeaderModule }
        deletes { $(".glyphicon-remove") }
    }

    public void createUser(String userName, String password, String mailAddress) {
        createUser.username = userName
        createUser.password = password
        createUser.email = mailAddress
        checkEnabled = true
        createUser.create().click()
    }

    public boolean UserCreationIsSuccessful() {
        return message.isDisplayed() && message.hasClass("alert-info")
    }

    public void deleteUser(int index) {
        deletes[index].click()
    }
}
