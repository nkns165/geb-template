package org.star.scenario

import geb.spock.GebReportingSpec
import org.star.helper.UserHelper
import org.star.page.TestCaseListPage

/**
 * Created by itagakishintarou on 2014/11/23.
 */
class TestcaseResistrationPractice extends GebReportingSpec{
    def "テストケース追加の練習"(){
        given: "一般ユーザでログインする"
        UserHelper.createDefaultUser(browser)
        when: "テストケースリスト画面を開く"
        header.openMenuTestCaseList()
        then:
        at TestCaseListPage
        when: "タグなしでテストケースを追加する"
        addTestCaseWithoutTag("テストケース名", "テストケースシナリオ")
        then: "Test Case詳細画面に正常登録のメッセージが表示される"
        isTestCaseCreationSuccessful()
    }
}
