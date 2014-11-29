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
        $("h1").text() == "Test Caseリスト"
    }
    static content = {
        header { module LoginHeaderModule }
        testCaseForm { $("form[role=form]") }
        // list
        filter { $("a", text: "Filter Me") }
        filterOpName { $("#filter\\2e op\\2e name") }
        filterName { $("#name") }
        filterOpTagsName { $("#filter\\2e op\\2e tags\\2e name") }
        filterTagsName { $("#tags\\2e name") }
        buttonActionFilter { $("#filterPaneForm > div > div.buttons > span:nth-child(3) > input") }
        testCaseItems { moduleList TestCaseRow, $("#testCaseRow") }
        editButtons { $(".glyphicon-eye-open") }
        deleteButtuns { $(".glyphicon-remove") }
        // create
        message { $("div.alert") }
        // edit
        buttonUpdate { $(".btn-primary", name: "update") }
    }

    public void addTestCase(String name, String scenario) {
        addTestCase(name, scenario, null)
    }

    public void addTestCase(String name, String scenario, String tag) {
        testCaseForm.name = name
        testCaseForm.scenario = scenario
        if (tag != null) {
            testCaseForm.tags = tag
        }
        testCaseForm.create().click()
    }

    public boolean isSuccessful() {
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

    public TestCaseRow[] searchTestCases(String name, String scenario, String[] tags) {
        return testCaseItems.findAll {
            (name == "" || it.name == name) && (scenario == "" || it.scenario == scenario) && (tags == "" || it.tags.split("\n").toList().containsAll(tags))
        }
    }

    public void updateTestcase(int index, String name, String scenario, String[] tags) {
        editButtons[index].click()
        testCaseForm.name = name
        sleep(1000)
        testCaseForm.scenario = scenario
        sleep(1000)
        if (tags.size() > 1) {
            testCaseForm.tags = tags
        } else {
            testCaseForm.tags = tags[0]
        }
        sleep(1000)
        buttonUpdate.click()
    }

    public void deleteTestCase() {
        deleteButtuns[0].click()
    }
}
