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
        header.testTag.click()
        createTag(tagName, tagDescription)
        then: "タグの登録に成功する"
        assert TagCreationIsSuccessful()

        when: "登録したテストタグを検索する"
        header.testTag.click()
        filterByName("Equal To", tagName)
        then: "削除をクリックする"
        def list = testTagItems
        list.size == 1
        withConfirm {testTagItems[0].delete.click()}
        then: "削除しましたメッセージが表示される"
        assert TagDeletionIsSuccessful()
    }

    def "使用済みのタグは削除できないよね"(){
        given: "一般ユーザでログインする"
        User user = UserHelper.createDefaultUser(browser)
        user.login()
        when: "タグを追加する"
        header.testTag.click()
        createTag(tagName, tagDescription)
        then: "タグの登録に成功する"
        assert TagCreationIsSuccessful()

        when: "登録したタグを持つテストケースを追加する"
        header.testCase.click()
        def testCaseName = "testCase_" + UUID.randomUUID()
        def testCaseScenario = "Scenario_tag_" + UUID.randomUUID()
        createTestCase(testCaseName, testCaseScenario, tagName)
        then: "登録完了メッセージを確認する"
        assert TestCaseCreationIsSuccessful()

        when: "メニューからテストタグをクリックする"
        header.testTag.click()
        then: "テストタグページが表示される"
        at TagPage
        when: "登録したテストタグを検索する"
        filterByName("Equal To", tagName)
        then: "削除をクリックする"
        def list = testTagItems
        list.size == 1
        withConfirm {testTagItems[0].delete.click()}
        then: "削除できませんでしたメッセージが表示されない"
        assert TagDeletionIsFailure()
    }
}
