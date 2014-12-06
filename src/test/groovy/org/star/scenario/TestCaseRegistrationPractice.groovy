package org.star.scenario

import geb.spock.GebReportingSpec
import org.star.domain.User
import org.star.helper.UserHelper
import org.star.page.TestCasePage

/**
 * Created by itagakishintarou on 2014/11/23.
 */
class TestCaseRegistrationPractice extends GebReportingSpec {
    def "テストケース追加の練習"() {
        given: "一般ユーザでログインする"
        User user = UserHelper.createDefaultUser(browser)
        user.login()
        when: "テストケースリスト画面を開く"
        header.testCase.click()
        then:
        at TestCasePage
        when: "タグなしでテストケースを追加する"
        createTestCase("テストケース名", "テストケースシナリオ")
        then: "Test Case詳細画面に正常登録のメッセージが表示される"
        TestCaseCreationIsSuccessful()
    }
}
