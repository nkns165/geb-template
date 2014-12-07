package org.star.scenario

import geb.Browser
import geb.spock.GebReportingSpec
import org.star.domain.Administrator
import org.star.domain.User
import org.star.helper.UserHelper
import org.star.page.FilterOption
import org.star.page.TopPage

/**
 * Created by itagakishintarou on 2014/12/07.
 */
class WhenDeletingUnnecessaryData extends GebReportingSpec {
    // user
    Administrator admin
    // test case
    String tagName = "Tag_" + UUID.randomUUID()
    String testCaseName = "TestCase_" + UUID.randomUUID()

    def setup() {
        admin = new Administrator(
                userName: "admin",
                password: "admin",
                mailAddress: "stac2014tamagawa@gmail.com",
                mailPassword: "tamagawa2014",
                browser: browser
        )
    }

    def "不要なタグを削除を削除するために、そのタグに紐づくテストケースを削除して、その後にタグを削除する"() {
        given: "管理者がログインすると、不要なタグが１つあり、そのタグに紐づくテストケースが３つある"
        "１つタグとそれに紐づく３つのテストケースを作成する"()
        when: "タグ画面に移動する"
        header.testTag.click()
        and: "不要なタグを名前でフィルターする"
        filterByName(FilterOption.Equal_To.toString(), tagName)
        then: "タグが１つ表示される"
        testTagItems.size() == 1
        when: "そのタグを削除する"
        deleteTag(0)
        then: "削除に失敗する"
        TagDeletionIsFailure()
        when: "テストケース画面に移動する"
        header.testCase.click()
        and: "不要なタグに紐づくテストケースをタグ名でフィルターする"
        filterByTag(FilterOption.Equal_To.toString(), tagName)
        then: "テストケースが３つ表示される"
        testCaseItems.size() == 3
        when: "１つ目のテストケースを削除する"
        deleteTestCase(0)
        then: "削除に成功する"
        TestCaseCreationIsSuccessful()
        when: "不要なタグに紐づくテストケースをタグ名でフィルターする"
        filterByTag(FilterOption.Equal_To.toString(), tagName)
        then: "テストケースが２つ表示される"
        testCaseItems.size() == 2
        when: "２つ目のテストケースを削除する"
        deleteTestCase(0)
        then: "削除に成功する"
        TestCaseCreationIsSuccessful()
        when: "不要なタグに紐づくテストケースをタグ名でフィルターする"
        filterByTag(FilterOption.Equal_To.toString(), tagName)
        then: "テストケースが１つ表示される"
        testCaseItems.size() == 1
        when: "３つ目のテストケースを削除する"
        deleteTestCase(0)
        then: "削除に成功する"
        TestCaseCreationIsSuccessful()
        when: "タグ画面に移動する"
        header.testTag.click()
        and: "不要なタグを名前でフィルターする"
        filterByName(FilterOption.Equal_To.toString(), tagName)
        then: "タグが１つ表示される"
        testTagItems.size() == 1
        when: "タグを削除する"
        deleteTag(0)
        then: "削除に成功する"
        TagDeletionIsSuccessful()
    }

    def "１つタグとそれに紐づく３つのテストケースを作成する"(){
        TopPage topPage = browser.to TopPage
        admin.login()
        header.testTag.click()
        createTag(tagName, "説明")
        header.testCase.click()
        createTestCase(testCaseName + "_1", "シナリオ", tagName)
        TestCaseCreationIsSuccessful()
        header.testCase.click()
        createTestCase(testCaseName + "_2", "シナリオ", tagName)
        TestCaseCreationIsSuccessful()
        header.testCase.click()
        createTestCase(testCaseName + "_3", "シナリオ", tagName)
        TestCaseCreationIsSuccessful()
    }
}
