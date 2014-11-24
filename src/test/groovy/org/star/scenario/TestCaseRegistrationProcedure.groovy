package org.star.scenario

import geb.spock.GebReportingSpec
import org.star.helper.UserHelper
import org.star.page.DashBoardPage
import org.star.page.TopPage


/**
 * Created by kenichiro_ota on 14/11/23.
 */
class TestCaseRegistrationProcedure extends GebReportingSpec {
    private def tag = "tag_" + UUID.randomUUID()
    private def description = "description_" + UUID.randomUUID()
    private def name = "name_" + UUID.randomUUID()
    private def scenario = "scenario_" + UUID.randomUUID()

    def "新規タグと関連づけたテストケースを下僕が起票して、先生がレビューして修正し、下僕に確認させる" () {

       given: "下僕がログインする"
       UserHelper.createUser(browser)
       when: "タグを追加する"
       header.openMenuTagList()
       addTag(tag, description)
       then: "Tag詳細画面に正常登録のメッセージが表示される"
       isTagCreationSuccessful()
       when: "追加したタグでテストケースの1つ目を追加する"
       header.openMenuTestCaseList()
       addTestCase("1_" + name, "1_" + scenario, tag)
       then: "Test Case詳細画面に正常登録のメッセージが表示される"
       isTestCaseCreationSuccessful()
       when: "追加したタグでテストケースの2つ目を追加する"
       header.openMenuTestCaseList()
       addTestCase("2_" + name, "2_" + scenario, tag)
       then: "Test Case詳細画面に正常登録のメッセージが表示される"
       isTestCaseCreationSuccessful()
       and: "下僕がログアウトする"
       header.logout()
       when: "先生がログインする"
       to TopPage
       login "admin", "admin"
       and: "追加したタグでテストケースを検索する"
       at DashBoardPage
       header.openMenuTestCaseList()
       searchByTag("Equal To", tag)
       then: "下僕が登録した2つのテストケースがリストに表示される"
       def list = searchTestCases("", "", tag)
       list.size() == 2
       list[0].name == "1_" + name && list[0].scenario == "1_" + scenario && list[0].tags == tag
       list[1].name == "2_" + name && list[1].scenario == "2_" + scenario && list[1].tags == tag
       when: "1つめのテストケースのシナリオを更新する"
       then: "Test Case詳細画面に正常登録のメッセージが表示される"
       and: "先生がログアウトする"
       when: "下僕がログインする"
       and: "テストケース名で更新したテストケースを検索する"
       then: "シナリオが先生の更新どおりであることを確認する"
    }
}