package org.star.page

import geb.Page

/**
 * Created by itagakishintarou on 2014/11/23.
 */
class TestCaseListPage extends Page{
    static url = "testCase"
    static at = {
        $("h1").text() == "Test Caseリスト"
    }
    static content = {
        addTestCaseForm { $("form[role=form]") }
        message { $("div.alert") }
    }

    public void addTestCaseWithoutTag(String name, String scenario) {
        addTestCaseForm.name = name
        addTestCaseForm.scenario = scenario
        addTestCaseForm.create().click()
    }

    public boolean isTestCaseCreationSuccessful() {
        waitFor { message.isDisplayed() }
        return message.isDisplayed() && message.hasClass("alert-info")
    }
}
