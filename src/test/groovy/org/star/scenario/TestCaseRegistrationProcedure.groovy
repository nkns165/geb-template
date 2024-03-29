package org.star.scenario.practice

import geb.spock.GebReportingSpec
import org.star.domain.User
import org.star.helper.UserHelper
import org.star.page.DashBoardPage
import org.star.page.FilterOption
import org.star.page.TopPage

/**
 * Created by kenichiro_ota on 14/11/23.
 */
class TestCaseRegistrationProcedure extends GebReportingSpec {
    // user
    User slave
    User teacher
    // test case
    String tag = "tag_" + UUID.randomUUID()
    String description = "description_" + UUID.randomUUID()
    String name = "name_" + UUID.randomUUID()
    String scenario = "scenario_" + UUID.randomUUID()

    def setup() {
        slave = UserHelper.createUser(browser, "slave_" + UUID.randomUUID(), UUID.randomUUID().toString(), "stac2014tamagawa@gmail.com", "tamagawa2014")
        teacher = UserHelper.createUser(browser, "teacher_" + UUID.randomUUID(), UUID.randomUUID().toString(), "stac2014tamagawa@gmail.com", "tamagawa2014")
    }

    def "新規タグと関連づけたテストケースを下僕が起票して、先生がレビューして修正し、下僕に確認させる"() {
        given: "下僕がログインする"
        slave.login()
        when: "タグを追加する"
        header.testTag.click()
        createTag(tag, description)
        then: "Tag詳細画面に正常登録のメッセージが表示される"
        TagCreationIsSuccessful()
        when: "追加したタグでテストケースの1つ目を追加する"
        header.testCase.click()
        createTestCase("1_" + name, "1_" + scenario, tag)
        then: "Test Case詳細画面に正常登録のメッセージが表示される"
        TestCaseCreationIsSuccessful()
        when: "追加したタグでテストケースの2つ目を追加する"
        header.testCase.click()
        createTestCase("2_" + name, "2_" + scenario, tag)
        then: "Test Case詳細画面に正常登録のメッセージが表示される"
        TestCaseCreationIsSuccessful()
        and: "下僕がログアウトする"
        slave.logout()
        when: "先生がログインする"
        to TopPage
        teacher.login()
        and: "追加したタグでテストケースを検索する"
        at DashBoardPage
        header.testCase.click()
        filterByTag(FilterOption.Equal_To.toString(), tag)
        then: "下僕が登録した2つのテストケースがリストに表示される"
        def list = searchTestCases(tags: [tag])
        list.size() == 2
        list[0].name == "1_" + name && list[0].scenario == "1_" + scenario && list[0].tags == tag
        list[1].name == "2_" + name && list[1].scenario == "2_" + scenario && list[1].tags == tag
        when: "1つめのテストケースのシナリオを更新する"
        updateTestCase(0, "1_" + name, "修正", [tag])
        then: "Test Case詳細画面に正常登録のメッセージが表示される"
        TestCaseCreationIsSuccessful()
        and: "先生がログアウトする"
        teacher.logout()
        when: "下僕がログインする"
        slave.login()
        and: "テストケース名で更新したテストケースを検索する"
        header.testCase.click()
        filterByName(FilterOption.Equal_To.toString(), "1_" + name)
        then: "シナリオが先生の更新どおりであることを確認する"
        testCaseItems[0].scenario == "修正"
    }
}