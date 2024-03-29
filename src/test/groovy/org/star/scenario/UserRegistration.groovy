package org.star.scenario

import geb.spock.GebReportingSpec
import org.star.domain.Administrator
import org.star.domain.User
import org.star.page.DashBoardPage
import org.star.page.TopPage
import org.star.page.UserListPage

/**
 * Created by tamagawa on 2014/11/23.
 */
class UserRegistration extends GebReportingSpec {

    String userName
    String password
    String mailAddress
    String mailPassword
    Administrator admin

    def setup() {
        userName = "user_" + UUID.randomUUID()
        password = UUID.randomUUID().toString()
        mailAddress = "stac2014tamagawa@gmail.com"
        mailPassword = "tamagawa2014"
        admin = new Administrator(userName: "admin", password: "admin", mailAddress: "stac2014tamagawa@gmail.com", mailPassword: "tamagawa2014", browser: browser)
    }

    // 一般ユーザが登録できること
    def "管理者ユーザでログインして一般ユーザを登録できる"() {
        given: "管理者ユーザでログインする"
        to TopPage
        login("admin", "admin")
        at DashBoardPage
        when: "Secure Userを作成ページを開く"
        header.admin.user.click()
        then:
        at UserListPage
        when: "新規ユーザを登録する"
        createUser(userName, password, mailAddress)
        then: "ユーザ一覧上で新規ユーザができたことが表示される"
        waitFor { message.isDisplayed() }
        UserCreationIsSuccessful()
        when: "管理者ユーザがログアウトする"
        header.logout.click()
        and: "新規登録したユーザでログインする"
        login(userName, password)
        then: "ログイン後、ダッシュボードが開く"
        at DashBoardPage
        and: "新着メールに新規登録したユーザー名が含まれることを確認する"
        User user = new User(userName: userName, password: password, mailAddress: mailAddress, mailPassword: mailPassword, browser: browser)
        user.receiveRegisteredEmail()
    }
}
