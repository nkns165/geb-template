package org.star.page

import geb.Page
import org.star.module.LoginHeaderModule
import org.star.module.TestCaseRow

/**
 * Created by itagakishintarou on 2014/11/23.
 */
class TestCasePage extends Page {
    static url = "testCase"
    static at = {
        $("h1").text().contains("Test Case")
    }
    static content = {
        header { module LoginHeaderModule }
        testCase { $("form[role=form]") }
        // list
        filter { $("a", text: "Filter Me") }
        filterOpName { $("#filter\\2e op\\2e name") }
        filterName { $("#name") }
        filterOpTagsName { $("#filter\\2e op\\2e tags\\2e name") }
        filterTagsName { $("#tags\\2e name") }
        buttonActionFilter { $("#filterPaneForm > div > div.buttons > span:nth-child(3) > input") }
        testCaseItems { moduleList TestCaseRow, $("#testCaseRow") }
        edits { $(".glyphicon-eye-open") }
        deletes(required: false) { $(".glyphicon-remove") }
        // create
        message { $("div.alert") }
        // edit
        update { $(".btn-primary", name: "update") }
    }

    public void addTestCase(String name, String scenario) {
        addTestCase(name, scenario, null)
    }

    public void addTestCase(String name, String scenario, String tag) {
        testCase.name = name
        testCase.scenario = scenario
        if (tag != null) {
            testCase.tags = tag
        }
        testCase.create().click()
    }

    public boolean TestCaseCreationIsSuccessful() {
        waitFor { message.isDisplayed() }
        return message.isDisplayed() && message.hasClass("alert-info")
    }

    public void searchByName(String option, String name) {
        filter.click()
        filterOpName = option
        filterName = name
        buttonActionFilter.click()
    }

    public void searchByTag(String option, String tags) {
        filter.click()
        filterOpTagsName = option
        filterTagsName = tags
        buttonActionFilter.click()
    }

    public TestCaseRow[] searchTestCases(Map param) {
        String name = param?.name ?: ""
        String scenario = param?.scenario ?: ""
        String[] tags = param?.tags ?: []

        return testCaseItems.findAll {
            (name == "" || it.name == name) && (scenario == "" || it.scenario == scenario) && (tags == [] || it.tags.split("\n").toList().containsAll(tags))
        }
    }

    public void updateTestCase(int index, String name, String scenario, String[] tags) {
        edits[index].click()
        sleep(1000) // 今の作りだとformのHTMLを非同期で書き換え当ているため、アプリケーション側にJavaScriptの終了オブザーバーとか作らないとSleepで待つしかない
        testCase.name = name
        sleep(1000)
        testCase.scenario = scenario
        sleep(1000)
        if (tags.size() > 1) {
            testCase.tags = tags
        } else {
            testCase.tags = tags[0]
        }
        sleep(1000)
        update.click()
    }

    public void deleteTestCase() {
        deletes[0].click()
    }
}
