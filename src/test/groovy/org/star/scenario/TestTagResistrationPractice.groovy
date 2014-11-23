package org.star.scenario

import geb.spock.GebReportingSpec
import org.star.helper.UserHelper
import org.star.page.TagListPage
import org.star.page.TestCaseListPage

/**
 * Created by itagakishintarou on 2014/11/23.
 */
class TestTagResistrationPractice extends GebReportingSpec{
    def "タグ追加の練習"(){
        given: "一般ユーザでログインする"
        UserHelper.createUser(browser)
        when: "タグリスト画面を開く"
        header.openMenuTagList()
        then:
        at TagListPage
        when: "タグを追加する"
        addTag("タグ名", "説明")
        then: "Tag詳細画面に正常登録のメッセージが表示される"
        isTagCreationSuccessful()
    }
}
