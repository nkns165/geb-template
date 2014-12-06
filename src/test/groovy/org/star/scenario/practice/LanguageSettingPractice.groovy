package org.star.scenario.practice

import geb.spock.GebReportingSpec
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxProfile
import org.star.domain.User
import org.star.helper.UserHelper

/**
 * Created by itagakishintarou on 2014/12/06.
 */
class LanguageSettingPractice extends GebReportingSpec {
    // user
    User slave
    User teacher
    // test case
    String tag = "tag_" + UUID.randomUUID()
    String description = "description_" + UUID.randomUUID()
    String name = "name_" + UUID.randomUUID()
    String scenario = "scenario_" + UUID.randomUUID()

    def setup() {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference( "intl.accept_languages", "no,en-us,en" );
        profile.setEnableNativeEvents(true);
        driver = new FirefoxDriver(profile);

        slave = UserHelper.createUser(browser, "slave_" + UUID.randomUUID(), UUID.randomUUID().toString(), "stac2014tamagawa@gmail.com", "tamagawa2014")
        teacher = UserHelper.createUser(browser, "teacher_" + UUID.randomUUID(), UUID.randomUUID().toString(), "stac2014tamagawa@gmail.com", "tamagawa2014")
    }
    def "ブラウザの言語設定によって英語表示されるテストの練習"(){
        when:"ログインしてヘッダーのテストケースをクリックする"
        teacher.login()
        header.testCase.click()
        then:"ページの見出しが英語になっている"
        $("h1").text() == "Test Case List"
    }
}