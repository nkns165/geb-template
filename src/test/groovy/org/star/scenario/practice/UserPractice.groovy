package org.star.scenario.practice

import geb.spock.GebReportingSpec
import org.star.domain.User
import org.star.helper.UserHelper
import org.star.page.TopPage

/**
 * Created by itagakishintarou on 2014/12/06.
 */
class UserPractice extends GebReportingSpec {
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

    def "ユーザー削除練習用"() {
        given:
        User admin = new User(userName: "admin", password: "admin", mailAddress: "stac2014tamagawa@gmail.com", mailPassword: "tamagawa2014", browser: browser)
        when:
        to TopPage
        admin.login()
        header.admin.user.click()
        for (int i = 0; i < 10; i++) {
            if (deletes.size() <= 5) {
                break
            }
            sleep(1500)
            deleteUser(5)
            sleep(500)
            driver.switchTo().alert().accept()
            sleep(1500)
        }
        then:
        true
    }
}
