package org.star.page

import geb.Page
import org.star.module.LoginHeaderModule

class UserListPage extends Page {
    static url = "secureUser"
    static at = {
        $("h1").text() == "Secure Userリスト"
    }

    static content = {
        addUserForm { $("form[role=form]") }
        checkEnabled { $("input[name=enabled]") }
        message { $("div.alert") }
        header { module LoginHeaderModule }
        deletes { $(".glyphicon-remove") }
    }

    public void addUser(String userName, String password, String mailAddress) {
        addUserForm.username = userName
        addUserForm.password = password
        addUserForm.email = mailAddress
        checkEnabled = true
        addUserForm.create().click()
    }

    public boolean isUserCreationSuccessful() {
        return message.isDisplayed() && message.hasClass("alert-info")
    }

    public void deleteUser(int index) {
        deletes[index].click()
    }
}
