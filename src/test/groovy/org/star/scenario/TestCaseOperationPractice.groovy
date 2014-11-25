package org.star.scenario
import geb.spock.GebReportingSpec
import org.star.page.DashBoardPage
import org.star.page.TestCasePage
import org.star.page.TopPage
import org.star.page.UserListPage
/**
 * Created by yoshimura on 2014/11/25.
 */
class TestCaseOperationPractice  extends GebReportingSpec{
    def username
    def password
    def mailAddress
    def testCaseName
    def testCaseScenario

    def setup(){
        username = "user_"+ UUID.randomUUID()
        password = "password_" + UUID.randomUUID()
        mailAddress = "yoshimura@yqs.jp"
        testCaseName = "TestCase_" + UUID.randomUUID()
        testCaseScenario = "Scenario" + UUID.randomUUID()
    }

    def "新規ユーザを登録して、そのユーザでテストケースを登録する"(){
        when: "管理者でログインする"
        to TopPage
        login "admin", "admin"
        then: "ダッシュボードが表示される"
        at DashBoardPage
        when: "ユーザメニューを表示する"
        header.openMenuUser()
        then: "ユーザリストが表示される"
        at UserListPage
        when: "ユーザを登録する"
        addUser(username, password, mailAddress)
        then: "登録完了メッセージを確認する"
        waitFor { message.isDisplayed() }
//        sleep(5000)
        assert isUserCreationSuccessful()
        when: "ログアウトする"
        header.logout()
        then: "トップページが表示される。"
        at TopPage

        when: "登録したユーザでログインする"
        login username, password
        then: "ログインできてダッシュボードが表示される。"
        at DashBoardPage
        when: "テストケースページを表示する"
        header.openMenuTestCase()
        then: "テストケースページが表示される"
        at TestCasePage
        when: "テストケースを登録する。"
        addTestCase(testCaseName,testCaseScenario, null)
        then: "登録完了メッセージを確認する"
        assert isSuccessful()
        when: "ログアウトする"
        header.logout()
        then: "トップページが表示される。"
        at TopPage
        when: "管理者でログインする"
        login "admin", "admin"
        then: "ダッシュボードが表示される"
        at DashBoardPage
        when: "テストケースページを開く"
        header.openMenuTestCase()
        then: "テストケースページが開く"
        at TestCasePage
        then: "登録されたテストケースが参照できる"
        searchTestCases(testCaseName, testCaseScenario, "").length == 1
        when: "ログアウトする"
        header.logout()
        then: "トップページが表示される"
        at TopPage
    }
}
