package org.star.page

import geb.Page
import org.star.module.LoginHeaderModule

/**
 * Created by itagakishintarou on 2014/11/23.
 */
class TagPage extends Page{
    static url = "tag"
    static at = {
        $("h1").text() == "Tagリスト"
    }
    static content = {
        addTagForm { $("form[role=form]") }
        message { $("div.alert") }
        header { module LoginHeaderModule }
        deleteButtuns { $(".glyphicon-remove") }
    }

    public void addTag(String name, String description) {
        addTagForm.name = name
        addTagForm.description = description
        addTagForm.create().click()
    }

    public boolean isTagCreationSuccessful() {
        waitFor { message.isDisplayed() }
        return message.isDisplayed() && message.hasClass("alert-info")
    }

    public void deleteTag(){
        deleteButtuns[0].click()
    }
}
