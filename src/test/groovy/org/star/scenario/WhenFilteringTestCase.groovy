package org.star.scenario

import geb.spock.GebReportingSpec
import org.star.domain.User
import org.star.page.DashBoardPage
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
    User admin

    def setup() {
        tagName = "TagName_" + UUID.randomUUID()
        testCaseName = "TestCase_" + UUID.randomUUID()
        testCaseScenario = "Scenario" + UUID.randomUUID()
        admin = new User(userName: "admin", password: "admin", mailAddress: "stac2014tamagawa@gmail.com", mailPassword: "tamagawa2014", browser: browser)
    }

    //作成中
    def "TestCaseページでTagNameをクリックすると、TagNameが設定されているTestCaseのみ表示される"() {
        when: "ログインする"
        to TopPage
        admin.login()
        then: "ダッシュボードが表示される"
        at DashBoardPage
        when: "Tagページを開く"
        header.openMenuTag()
        then: "Tagページが表示される"
        at TagPage
        when: "Tagを追加する"
        createTag(tagName, "説明")
        then: "Tagが追加される"
        TagCreationIsSuccessful()
        when: "TestCaseページを開く"
        header.openMenuTestCase()
        //to TestCasePage
        then: "TestCaseページが表示される"
        at TestCasePage
        when: "作成したTagを指定したTestCaseを追加する"
        addTestCase("1_" + testCaseName, "1_" + testCaseScenario, tagName)
        then: "Test Case詳細画面に正常登録のメッセージが表示される"
        TestCaseCreationIsSuccessful()
        when: "作成したTagを指定したTestCaseをもう1件追加する"
        header.openMenuTestCase()
        addTestCase("2_" + testCaseName, "2_" + testCaseScenario, tagName)
        then: "Test Case詳細画面に正常登録のメッセージが表示される"
        TestCaseCreationIsSuccessful()
        when: "TestCaseリストから最初に追加したTestCaseのTagNameリンクをクリックする"
        header.openMenuTestCase()
        searchByTag("Equal To", tagName)
        then: "TestCase詳細ページに遷移する"
        and: "TestCase詳細ページに作成した2件のTestCaseのみ表示されていることを確認する"
    }
}
