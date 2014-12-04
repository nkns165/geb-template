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
        addTag { $("form[role=form]") }
        message { $("div.alert") }
        header { module LoginHeaderModule }
        testTagItems { moduleList TestTagRow, $("div.table-responsive tbody > tr") }
        deletes(required: false) { $(".glyphicon-remove") }

        filterMe {$("a", text:"Filter Me")}
        nameFilterOperation {$("select", id:"filter.op.name")}
        descriptionFilterOperation {$("select", id:"filter.op.description")}
        nameFilter {$("input", name:"filter.name")}
        descriptionFilter {$("input", name:"filter.description")}
        applyFilter {$("input", value:"Apply")}
    }

    public void addTag(String name, String description) {
        addTag.name = name
        addTag.description = description
        addTag.create().click()
    }

    public String createTag(String name, String description) {
        addTag(name, description)
        // TODO アプリケーション名外出し
        Process process = "heroku logs --app fathomless-stream-3131".execute()
        process.waitFor()
        Matcher tagIdMatcher = (process.text =~ /(?s)aop.TagServiceGet.*? : (\d+)/)
        return tagIdMatcher[(int) tagIdMatcher.size() - 1][1]
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
        deletes[0].click()
    }

    public boolean TagDeletionIsSuccessful(){
        waitFor { message.isDisplayed()}
        return message.isDisplayed() && message.text().contains("削除しました。")
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
