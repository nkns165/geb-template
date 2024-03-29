package org.star.scenario

import geb.spock.GebReportingSpec
import org.star.domain.User
import org.star.page.DashBoardPage
import org.star.page.FilterOption
import org.star.page.TagPage
import org.star.page.TestCasePage
import org.star.page.TopPage

/**
 * Created by urayama on 2014/12/01.
 */
class WhenFilteringTestCase extends GebReportingSpec {
    String tagName
    String testCaseName
    String testCaseScenario
    String tagDescription
    User admin

    def setup() {
        tagName = "TagName_" + UUID.randomUUID()
        tagDescription = "description_" + UUID.randomUUID()
        testCaseName = "TestCase_" + UUID.randomUUID()
        testCaseScenario = "Scenario" + UUID.randomUUID()
        admin = new User(userName: "admin", password: "admin", mailAddress: "stac2014tamagawa@gmail.com", mailPassword: "tamagawa2014", browser: browser)
    }

    def "TestCaseページでTagNameをクリックすると、TagNameが設定されているTestCaseのみ表示される"() {
        when: "ログインする"
        to TopPage
        admin.login()
        then: "ダッシュボードが表示される"
        at DashBoardPage
        when: "Tagページを開く"
        header.testTag.click()
        then: "Tagページが表示される"
        at TagPage
        when: "Tagを追加する"
        createTag(tagName, tagDescription)
        then: "Tagが追加される"
        TagCreationIsSuccessful()
        when: "TestCaseページを開く"
        header.testCase.click()
        then: "TestCaseページが表示される"
        at TestCasePage
        when: "作成したTagを指定したTestCaseを追加する"
        createTestCase("1_" + testCaseName, "1_" + testCaseScenario, tagName)
        then: "Test Case詳細画面に正常登録のメッセージが表示される"
        TestCaseCreationIsSuccessful()
        when: "作成したTagを指定したTestCaseをもう1件追加する"
        header.testCase.click()
        createTestCase("2_" + testCaseName, "2_" + testCaseScenario, tagName)
        then: "Test Case詳細画面に正常登録のメッセージが表示される"
        TestCaseCreationIsSuccessful()
        when: "TestCaseリストから最初に追加したTestCaseのTagNameリンクをクリックする"
        header.testCase.click()
        filterByTag(FilterOption.Equal_To.toString(), tagName)
        then: "TestCase詳細ページに作成した2件のTestCaseのみ表示されていることを確認する"
        testCaseItems[0].tags == tagName
        testCaseItems[1].tags == tagName
        testCaseItems.size== 2
    }
}
