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
    static final String testCaseDetailHeadingText = "Test Case詳細"
    static content = {
        header { module LoginHeaderModule }
        testCase { $("form[role=form]") }
        testCaseHeading { $("#form h1") }
        // list
        filter { $("a", text: "Filter Me") }
        filterOpName { $("select", id:"filter.op.name") }
        filterName { $("input", id:"name") }
        filterOpTagsName { $("select", id:"filter.op.tags.name") }
        filterTagsName { $("input", id:"tags.name") }
        next(wait:true, required: false){$(".nextLink")}
        buttonActionFilter { $("#filterPaneForm input", name:"_action_filter") }
        testCaseItems { moduleList TestCaseRow, $("#testCaseRow") }
        // create
        message { $("div.alert") }
        // edit
        update { $(".btn-primary", name: "update") }
    }

    public void createTestCase(String name, String scenario, String tag = null) {
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

    public void filterByName(String option, String name) {
        filter.click()
        filterOpName = option
        filterName = name
        buttonActionFilter.click()
    }

    public void filterByTag(String option, String tags) {
        filter.click()
        filterOpTagsName = option
        filterTagsName = tags
        buttonActionFilter.click()
    }

    public List<TestCaseRow> searchTestCases(Map param) {
        String name = param?.name ?: ""
        String scenario = param?.scenario ?: ""
        List<String> tags = param?.tags ?: []

        List<TestCaseRow> result = testCaseItems.findAll {
            (name == "" || it.name == name) && (scenario == "" || it.scenario == scenario) && (tags == [] || it.tags.split("\n").toList().containsAll(tags))
        }
        if(result){
            return result
        } else if(find(".nextLink")){
            next.click()
            sleep(3000)
            searchTestCases(name:name, scenario:scenario, tags:tags)
        } else{
            return []
        }
    }

    public void updateTestCase(int index, String name, String scenario, List<String> tags) {
        testCaseItems[index].edit.click()
        waitFor { testCaseHeading.text() == testCaseDetailHeadingText }
        testCase.name = name
        testCase.scenario = scenario
        if (tags.size() > 1) {
            testCase.tags = tags
        } else {
            testCase.tags = tags[0]
        }
        update.click()
    }

    public void deleteTestCase(int index = 0) {
        withConfirm(true) {testCaseItems[index].delete.click()}
    }
}
