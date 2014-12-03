package org.star.page

import geb.Page
import org.star.module.LoginHeaderModule

import java.util.regex.Matcher

/**
 * Created by itagakishintarou on 2014/11/23.
 */
class TagPage extends Page {
    static url = "tag"
    static at = {
        $("h1").text() == "Tagリスト"
    }
    static content = {
        addTag { $("form[role=form]") }
        message { $("div.alert") }
        header { module LoginHeaderModule }
        deletes(required: false) { $(".glyphicon-remove") }
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

    public boolean isTagCreationSuccessful() {
        waitFor { message.isDisplayed() }
        return message.isDisplayed() && message.hasClass("alert-info")
    }

    public boolean isTagCreationSuccessful(String tagId) {
        println message.text()
        return isTagCreationSuccessful() &&
                message.text().contains("Tag(id:${tagId})を作成しました。")
    }

    public void deleteTag() {
        deletes[0].click()
    }
}
