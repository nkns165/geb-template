package org.star.scenario

import geb.spock.GebReportingSpec
import org.star.domain.User
import org.star.page.DashBoardPage
import org.star.page.TopPage
import org.star.page.TagPage
import org.star.page.TestCasePage

/**
 * Created by urayama on 2014/12/01.
 */
class WhenTestCaseTagFilter extends GebReportingSpec {
    String tagName
    String testCaseName
    String testCaseScenario
    User admin

    def setup() {
        tagName = "TagName_" + UUID.randomUUID()
        testCaseName = "TestCase_" + UUID.randomUUID()
        testCaseScenario = "Scenario" + UUID.randomUUID()
        admin = new User(username: "admin", password: "admin", mailAddress: "stac2014tamagawa@gmail.com", mailPassword: "tamagawa2014", browser: browser)
    }

    //作成中
    def "TestCaseページでTagNameをクリックすると、TagNameが設定されているTestCaseのみ表示される"(){
        when:"ログインする"
        to TopPage
        admin.login()
        then:"ダッシュボードが表示される"
        at DashBoardPage
        when:"Tagページを開く"
        to TagPage
        then:"Tagページが表示される"
        at TagPage
        when:"Tagを追加する"
        addTag(tagName, "説明")
        then:"Tagが追加される"
        isTagCreationSuccessful
        when:"TestCaseページを開く"
        to TestCasePage
        then:"TestCaseページが表示される"
        at TestCasePage
        when:"作成したTagを指定したTestCaseを追加する"
        addTestCase("1_" + testCaseName, "1_" + testCaseScenario, tagName)
        then:"TestCaseリストにTestCaseが追加される"
        isSuccessful()      //TestCaseのチェックか分かりにくい
        when:"作成したTagを指定したTestCaseをもう1件追加する"
        addTestCase("2_" + testCaseName, "2_" + testCaseScenario, tagName)
        then:"TestCaseリストにTestCaseが追加される"
        isSuccessful()      //TestCaseのチェックか分かりにくい
        when:"TestCaseリストから最初に追加したTestCaseのTagNameリンクをクリックする"
        then:"TestCase詳細ページに遷移する"
        and:"TestCase詳細ページに作成した2件のTestCaseのみ表示されていることを確認する"
    }
}
