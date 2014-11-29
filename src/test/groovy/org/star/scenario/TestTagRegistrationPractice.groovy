package org.star.scenario

import geb.spock.GebReportingSpec
import org.star.domain.User
import org.star.helper.UserHelper
import org.star.page.TagPage

/**
 * Created by itagakishintarou on 2014/11/23.
 */
class TestTagRegistrationPractice extends GebReportingSpec {
    def "タグ追加の練習"() {
        given: "一般ユーザでログインする"
        User user = UserHelper.createDefaultUser(browser)
        user.login()
        when: "タグリスト画面を開く"
        header.openMenuTag()
        then:
        at TagPage
        when: "タグを追加する"
        addTag("タグ名" + UUID.randomUUID(), "説明")
        then: "Tag詳細画面に正常登録のメッセージが表示される"
        isTagCreationSuccessful()
    }
}
