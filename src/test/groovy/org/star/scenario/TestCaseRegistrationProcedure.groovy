package org.star.scenario

import geb.spock.GebReportingSpec
import org.star.domain.User
import org.star.helper.UserHelper
import org.star.page.DashBoardPage
import org.star.page.TopPage


/**
 * Created by kenichiro_ota on 14/11/23.
 */
class TestCaseRegistrationProcedure extends GebReportingSpec {
    private def tag = "tag_" + UUID.randomUUID()
    private def description = "description_" + UUID.randomUUID()
    private def testcaseName = "testcaseName_" + UUID.randomUUID()
    private def testcaseScenario = "testcaseScenario_" + UUID.randomUUID()

    def "新規タグと関連づけたテストケースを下僕が起票して、先生がレビューして修正し、下僕に確認させる" () {

       given: "下僕がログインする"
       User user = UserHelper.createDefaultUser(browser)
       user.login()
       when: "タグを追加する"
       header.openMenuTagList()
       addTag(tag, description)
       then: "Tag詳細画面に正常登録のメッセージが表示される"
       isTagCreationSuccessful()
       when: "追加したタグでテストケースの1つ目を追加する"
       header.openMenuTestCaseList()
       addTestCaseWithoutTag(testcaseName, testcaseScenario, tag)
       then: "Test Case詳細画面に正常登録のメッセージが表示される"
       isTestCaseCreationSuccessful()
       when: "追加したタグでテストケースの2つ目を追加する"
       then: "Test Case詳細画面に正常登録のメッセージが表示される"
       and: "下僕がログアウトする"
       when: "先生がログインする"
       and: "追加したタグでテストケースを検索する"
       then: "下僕が登録した2つのテストケースがリストに表示される"
       when: "1つめのテストケースのシナリオを更新する"
       then: "Test Case詳細画面に正常登録のメッセージが表示される"
       and: "先生がログアウトする"
       when: "下僕がログインする"
       and: "テストケース名で更新したテストケースを検索する"
       then: "シナリオが先生の更新どおりであることを確認する"
    }

    def "ユーザーの作成練習"() {
        when: "ユーザーを追加する"
       User user = UserHelper.createDefaultUser(browser)
        user.login()
        then: "ダッシュボードページが表示される"
        browser.at DashBoardPage
        when: "ログアウトする"
        user.logout()
        then:
        browser.at TopPage
        when: "もっかいログインする"
        user.login()
        then: "ダッシュボードページが表示される"
        browser.at DashBoardPage
    }
}