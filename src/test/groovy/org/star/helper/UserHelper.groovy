package org.star.helper

import geb.Browser
import org.star.domain.User
import org.star.page.DashBoardPage
import org.star.page.TopPage
import org.star.page.UserListPage

import javax.mail.Folder
import javax.mail.FolderNotFoundException
import javax.mail.Message
import javax.mail.Session
import javax.mail.Store

/**
 * Created by tamagawa on 2014/11/23.
 */
class UserHelper {

    public static User createDefaultUser(Browser browser) {
        String username = "user_" + UUID.randomUUID()
        String password = UUID.randomUUID().toString()
        String mailAddress = "stac2014tamagawa@gmail.com"
        String mailPassword = "tamagawa2014"

        createUser(browser, username, password, mailAddress, mailPassword)
    }

    public static User createUser(Browser browser, String username, String password, String mailAddress, String mailPassword) {
        TopPage topPage = browser.to TopPage
        topPage.login "admin", "admin"
        DashBoardPage dashBoardPage = browser.at DashBoardPage
        dashBoardPage.header.openMenuUser()

        UserListPage userListPage = browser.at UserListPage
        userListPage.addUser(username, password, mailAddress)
        browser.waitFor { userListPage.message.isDisplayed() }
        userListPage.header.logout()

        new User(username: username, password: password, mailAddress: mailAddress, mailPassword: mailPassword, browser: browser)
    }

    public static String getBodyFromMail(String mailAddress, String mailPassword) {
        String host = "imap.gmail.com"
        int port = 993
        String targetFolder = "INBOX"

        Properties properties = System.getProperties()
        Session session = Session.getInstance(properties, null)

        Store store = session.getStore("imaps")
        store.connect(host, port, mailAddress, mailPassword)
        Folder folder = store.getFolder(targetFolder)
        if (folder.exists()) {
            folder.open(Folder.READ_ONLY)
            if (folder.getMessageCount() > 0) {
                for (Message message : folder.getMessages()) {
                    return message.getContent()
                }
            } else {
                throw new RuntimeException("Folder is empty")
            }
            folder.close(false)
        } else {
            throw new FolderNotFoundException("Folder ${targetFolder} does not exist.")
        }
        store.close()
    }
}