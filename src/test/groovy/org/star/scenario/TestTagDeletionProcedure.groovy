package org.star.scenario

import geb.spock.GebReportingSpec
import org.star.domain.User
import org.star.helper.UserHelper
import org.star.page.TagPage

/**
 * Created by yoshimura on 2014/12/01.
 */
class TestTagDeletionProcedure extends GebReportingSpec {

    String tagName
    String tagDescription

    def setup() {
        tagName = "tag_" + UUID.randomUUID()
        tagDescription = "description_" + UUID.randomUUID()
    }

    def "未使用のタグは削除することができるよね"(){
        given: "一般ユーザでログインする"
        User user = UserHelper.createDefaultUser(browser)
        user.login()
        when: "タグを追加する"
        header.openMenuTag()
        addTag(tagName, tagDescription)
        then: "タグの登録に成功する"
        assert TagCreationIsSuccessful()

        when: "登録したテストタグを検索する"
        header.openMenuTag()
        filterByName("Equal To", tagName)
        then: "削除をクリックする"
        def list = testTagItems
        list.size == 1
        withConfirm {list[0].deleteButton.click()}
        then: "削除しましたメッセージが表示される"
        assert TagDeletionIsSuccessful()
    }

    def "使用済みのタグは削除できないよね"(){
        given: "一般ユーザでログインする"
        User user = UserHelper.createDefaultUser(browser)
        user.login()
        when: "タグを追加する"
        header.openMenuTag()
        addTag(tagName, tagDescription)
        then: "タグの登録に成功する"
        assert TagCreationIsSuccessful()

        when: "登録したタグを持つテストケースを追加する"
        header.openMenuTestCase()
        def testCaseName = "testCase_" + UUID.randomUUID()
        def testCaseScenario = "Scenario_tag_" + UUID.randomUUID()
        addTestCase(testCaseName, testCaseScenario, tagName)
        then: "登録完了メッセージを確認する"
        assert TestCaseCreationIsSuccessful()

        when: "メニューからテストタグをクリックする"
        header.openMenuTag()
        then: "テストタグページが表示される"
        at TagPage
        when: "登録したテストタグを検索する"
        filterByName("Equal To", tagName)
        then: "削除をクリックする"
        def list = testTagItems
        list.size == 1
        withConfirm {list[0].deleteButton.click()}
        then: "削除できませんでしたメッセージが表示されない"
        assert TagDeletionIsFailure()
    }
}