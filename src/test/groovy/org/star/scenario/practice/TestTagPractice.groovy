package org.star.scenario.practice

import geb.spock.GebReportingSpec
import org.star.domain.User
import org.star.helper.UserHelper
import org.star.page.TagPage

/**
 * Created by itagakishintarou on 2014/11/23.
 */
class TestTagPractice extends GebReportingSpec {
    def "タグ追加の練習"() {
        given: "一般ユーザでログインする"
        User user = UserHelper.createDefaultUser(browser)
        user.login()
        when: "タグリスト画面を開く"
        header.testTag.click()
        then:
        at TagPage
        when: "タグを追加する"
        createTag("タグ名" + UUID.randomUUID(), "説明")
        then: "Tag詳細画面に正常登録のメッセージが表示される"
        TagCreationIsSuccessful()
    }

    /**
     * このシナリオを上のものと別に追加する意味はあまりないですが、
     * ログを確認するシナリオのサンプルとして新たに追加しました。
     */
    def "タグ追加の練習（ID確認まで行う）"() {
        given: "一般ユーザでログインする"
        User user = UserHelper.createDefaultUser(browser)
        user.login()
        when: "タグリスト画面を開く"
        header.testTag.click()
        then:
        at TagPage
        when: "タグを追加する"
        def tagId = createTagAndGetTagId("タグ名" + UUID.randomUUID(), "説明")
        then: "Tag詳細画面に正常登録のメッセージが表示される"
        TagCreationIsSuccessful(tagId)
    }

    def "タグ削除練習"() {
        when:
        teacher.login()
        header.testTag.click()
        for (int i = 0; i < 10; i++) {
            if (!testTagItems) {
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
