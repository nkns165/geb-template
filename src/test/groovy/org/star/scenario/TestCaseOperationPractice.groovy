package org.star.scenario

import geb.spock.GebReportingSpec
import org.star.domain.Administrator
import org.star.domain.User
import org.star.page.DashBoardPage
import org.star.page.TestCasePage
import org.star.page.TopPage
import org.star.page.UserListPage

/**
 * Created by yoshimura on 2014/11/25.
 */
class TestCaseOperationPractice extends GebReportingSpec {
    String username
    String password
    String mailAddress
    String mailPassword
    String testCaseName
    String testCaseScenario
    Administrator admin

    def setup() {
        username = "user_" + UUID.randomUUID()
        password = "password_" + UUID.randomUUID()
        mailAddress = "stac2014tamagawa@gmail.com"
        mailPassword = "tamagawa2014"
        testCaseName = "TestCase_" + UUID.randomUUID()
        testCaseScenario = "Scenario" + UUID.randomUUID()
        admin = new Administrator(username: "admin", password: "admin", mailAddress: "stac2014tamagawa@gmail.com", mailPassword: "tamagawa2014", browser: browser)
    }

    def "新規ユーザを登録して、そのユーザでテストケースを登録する"() {
        when: "管理者でログインする"
        to TopPage
        admin.login()
        then: "ダッシュボードが表示される"
        at DashBoardPage
        when: "ユーザを登録する"
        User user = new User(username: username, password: password, mailAddress: mailAddress, mailPassword: mailPassword, browser: browser)
        admin.addUser(user)
        then: "登録完了メッセージを確認する"
        waitFor { message.isDisplayed() }
        isUserCreationSuccessful()
        when: "ログアウトする"
        admin.logout()
        then: "トップページが表示される。"
        at TopPage

        when: "登録したユーザでログインする"
        user.login()
        then: "ログインできてダッシュボードが表示される。"
        at DashBoardPage
        when: "テストケースページを表示する"
        header.openMenuTestCase()
        then: "テストケースページが表示される"
        at TestCasePage
        when: "テストケースを登録する。"
        addTestCase(testCaseName, testCaseScenario)
        then: "登録完了メッセージを確認する"
        assert isSuccessful()
        when: "ログアウトする"
        user.logout()
        then: "トップページが表示される。"
        at TopPage
        when: "管理者でログインする"
        admin.login()
        then: "ダッシュボードが表示される"
        at DashBoardPage
        when: "テストケースページを開く"
        header.openMenuTestCase()
        then: "テストケースページが開く"
        at TestCasePage
        then: "登録されたテストケースが参照できる"
        searchTestCases(testCaseName, testCaseScenario, "").length == 1
        when: "ログアウトする"
        admin.logout()
        then: "トップページが表示される"
        at TopPage
    }
}
