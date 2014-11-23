package org.star.scenario

import geb.spock.GebReportingSpec
import org.star.helper.UserHelper
import org.star.page.DashBoardPage
import org.star.page.TopPage
import org.star.page.UserListPage

/**
 * Created by tamagawa on 2014/11/23.
 */
class UserRegistration extends GebReportingSpec {

    def userName
    def password
    def mailAddress

    def setup() {
        userName = "user_" + UUID.randomUUID()
        password = UUID.randomUUID().toString()
        mailAddress = "hiroko.tamagawa@shiftinc.jp"
    }

    // 一般ユーザが登録できること
    def "管理者ユーザでログインして一般ユーザを登録できる"() {
        given: "管理者ユーザでログインする"
        to TopPage
        login "admin", "admin"
        at DashBoardPage
        when: "ユーザ一覧画面を開く"
        header.openMenuUser()
        and: "新規ユーザを登録する"
        addUser(userName, password, mailAddress)
        then: "ユーザ一覧上で新規ユーザができたことが表示される"
        waitFor { message.isDisplayed() }
        assert isUserCreationSuccessful()
        when: "管理者ユーザがログアウトする"
        header.logout()
        and: "新規登録したユーザでログインする"
        login userName, password
        then: "ログイン後、ダッシュボードが開く"
        at DashBoardPage
    }
}
