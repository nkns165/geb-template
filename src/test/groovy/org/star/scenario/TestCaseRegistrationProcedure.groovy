package org.star.scenario

import geb.spock.GebReportingSpec
import org.star.domain.Administrator
import org.star.domain.User
import org.star.helper.UserHelper
import org.star.page.DashBoardPage
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
        header.openMenuTag()
        addTag(tag, description)
        then: "Tag詳細画面に正常登録のメッセージが表示される"
        isTagCreationSuccessful()
        when: "追加したタグでテストケースの1つ目を追加する"
        header.openMenuTestCase()
        addTestCase("1_" + name, "1_" + scenario, tag)
        then: "Test Case詳細画面に正常登録のメッセージが表示される"
        isTestCaseCreationSuccessful()
        when: "追加したタグでテストケースの2つ目を追加する"
        header.openMenuTestCase()
        addTestCase("2_" + name, "2_" + scenario, tag)
        then: "Test Case詳細画面に正常登録のメッセージが表示される"
        isTestCaseCreationSuccessful()
        and: "下僕がログアウトする"
        slave.logout()
        when: "先生がログインする"
        to TopPage
        teacher.login()
        and: "追加したタグでテストケースを検索する"
        at DashBoardPage
        header.openMenuTestCase()
        searchByTag("Equal To", tag)
        then: "下僕が登録した2つのテストケースがリストに表示される"
        def list = searchTestCases("", "", tag)
        list.size() == 2
        list[0].name == "1_" + name && list[0].scenario == "1_" + scenario && list[0].tags == tag
        list[1].name == "2_" + name && list[1].scenario == "2_" + scenario && list[1].tags == tag
        when: "1つめのテストケースのシナリオを更新する"
        updateTestcase(0, "1_" + name, "修正", tag)
        then: "Test Case詳細画面に正常登録のメッセージが表示される"
        isTestCaseCreationSuccessful()
        and: "先生がログアウトする"
        teacher.logout()
        when: "下僕がログインする"
        slave.login()
        and: "テストケース名で更新したテストケースを検索する"
        header.openMenuTestCase()
        searchByName("Equal To", "1_" + name)
        then: "シナリオが先生の更新どおりであることを確認する"
        testCaseItems[0].scenario == "修正"
    }

    def "テストケース削除練習"() {
        when:
        teacher.login()
        header.openMenuTestCase()
        for (int i = 0; i < 100; i++) {
            if (!deleteButtuns) {
                break
            }
            sleep(1500)
            deleteTestCase()
            sleep(500)
            driver.switchTo().alert().accept()
            sleep(1500)
        }
        then:
        true
    }

    def "タグ削除練習"() {
        when:
        teacher.login()
        header.openMenuTag()
        for (int i = 0; i < 100; i++) {
            if (!deleteButtuns) {
                break
            }
            sleep(1500)
            deleteTag()
            sleep(500)
            driver.switchTo().alert().accept()
            sleep(1500)
        }
        then:
        true
    }
}