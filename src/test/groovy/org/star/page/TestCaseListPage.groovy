package org.star.page

import geb.Page
import org.star.module.LoginHeaderModule
import org.star.module.TestCaseRow

/**
 * Created by itagakishintarou on 2014/11/23.
 */
class TestCaseListPage extends Page{
    static url = "testCase"
    static at = {
        $("h1").text() == "Test Caseリスト"
    }
    static content = {
        header { module LoginHeaderModule }
        // list
        filter { $("a", text:"Filter Me") }
        filterOpTagsName{ $("#filter\\2e op\\2e tags\\2e name") }
        filterTagsName{ $("#tags\\2e name") }
        buttonActionFilter{ $("#filterPaneForm > div > div.buttons > span:nth-child(3) > input") }
        testCaseItems { moduleList TestCaseRow, $("#testCaseRow") }
        // create
        message { $("div.alert") }
        addTestCaseForm { $("form[role=form]") }
    }

    public void addTestCase(String name, String scenario) {
        addTestCaseWithoutTag(name, scenario, null)
    }

    public void addTestCase(String name, String scenario, String tag) {
        addTestCaseForm.name = name
        addTestCaseForm.scenario = scenario
        if(tag != null){
            addTestCaseForm.tags = tag
        }
        addTestCaseForm.create().click()
    }

    public boolean isTestCaseCreationSuccessful() {
        waitFor { message.isDisplayed() }
        return message.isDisplayed() && message.hasClass("alert-info")
    }

    public void searchByTag(String option, String tags){
        filter.click()
        filterOpTagsName = option
        filterTagsName = tags
        buttonActionFilter.click()
    }

    public TestCaseRow[] searchTestCases(String name, String scenario, String[] tags){
        return testCaseItems.findAll{
            println it.tags.split("\n").toList()
            println "input:" + tags
            (name == "" || it.name == name) && (scenario == "" || it.scenario == scenario) && (tags == "" || it.tags.split("\n").toList().containsAll(tags) )
        }
    }
}
