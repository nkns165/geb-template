package org.star.scenario

import geb.spock.GebReportingSpec
import org.star.domain.User
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
    def mailPassword

    def setup() {
        userName = "user_" + UUID.randomUUID()
        password = UUID.randomUUID().toString()
        mailAddress = "stac2014tamagawa@gmail.com"
        mailPassword = "tamagawa2014"
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
        //and: "adminメニューが表示されないことを確認する"
        //assert header.menuAdmin().isDisplayed()
        and: "新着メールに新規登録したユーザー名が含まれることを確認する"
        assert UserHelper.getBodyFromMail(mailAddress, mailPassword).contains(userName)
    }

    def "ユーザー削除練習用"() {
        given:
        User admin = new User(username: "admin", password: "admin", mailAddress: "hiroko.tamagawa@shiftinc.jp", browser: browser)
        when:
        to TopPage
        admin.login()
        header.openMenuUser()
        for (int i = 0; i < 100; i++) {
            sleep(1500)
            deleteUser(5)
            sleep(500)
            driver.switchTo().alert().accept()
            sleep(1500)
        }
        then:
        true
    }
}
