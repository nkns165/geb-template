package org.star.page

import geb.Page
import org.star.module.LoginHeaderModule
import org.star.module.TestTagRow

import java.util.regex.Matcher

/**
 * Created by itagakishintarou on 2014/11/23.
 */
class TagPage extends Page {
    static url = "tag"
    static at = {
        $("h1").text().contains("Tag")
    }
    static content = {
        tagForm { $("form[role=form]") }
        message { $("div.alert") }
        header { module LoginHeaderModule }
        testTagItems { moduleList TestTagRow, $("div.table-responsive tbody > tr") }
        filterMe {$("a", text:"Filter Me")}
        nameFilterOperation {$("select", id:"filter.op.name")}
        descriptionFilterOperation {$("select", id:"filter.op.description")}
        nameFilter {$("input", name:"filter.name")}
        descriptionFilter {$("input", name:"filter.description")}
        applyFilter {$("input", value:"Apply")}
    }

    public void createTag(String name, String description) {
        tagForm.name = name
        tagForm.description = description
        tagForm.create().click()
    }

    public String logging() {
        // TODO アプリケーション名外出し
        // Process process = "cmd /c start heroku.bat logs --app fathomless-stream-3131".execute()
        def process =  "cmd /c heroku.bat logs --app fathomless-stream-3131".execute()
        //process.waitFor()
        return process.text
    }

    public boolean TagCreationIsSuccessful() {
        waitFor { message.isDisplayed() }
        return message.isDisplayed() && message.hasClass("alert-info")
    }

    public boolean TagCreationIsSuccessful(String tagId) {
        println message.text()
        return TagCreationIsSuccessful() &&
                message.text().contains("Tag(id:${tagId})を作成しました。")
    }

    public void deleteTag() {
        testTagItems[0].delete.click()
    }

    public boolean TagDeletionIsSuccessful(){
        waitFor { message.isDisplayed()}
        return message.isDisplayed() && message.text().contains("削除しました。")
    }

    public boolean TagDeletionIsFailure(){
        waitFor { message.isDisplayed()}
        return message.isDisplayed() && message.text().contains("削除できませんでした。")
    }

    public void filterByName(String option, String name) {
        filterMe.click()
        nameFilterOperation = option
        nameFilter = name
        applyFilter.click()
    }

    public void filterByDescription(String option, String description) {
        filterMe.click()
        descriptionFilterOperation = option
        descriptionFilter = description
        applyFilter.click()
    }

}
