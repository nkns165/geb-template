package org.star.helper

import geb.Browser
import geb.Page
import org.openqa.selenium.WebDriver
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

    public static void createUser(Browser browser) {
        def userName = "user_" + UUID.randomUUID()
        def password = UUID.randomUUID().toString()
        def mailAddress = "hiroko.tamagawa@shiftinc.jp"

        TopPage topPage = browser.to TopPage
        topPage.login "admin", "admin"
        DashBoardPage dashBoardPage = browser.at DashBoardPage
        dashBoardPage.header.openMenuUser()

        UserListPage userListPage = browser.at UserListPage
        userListPage.addUser(userName, password, mailAddress)
        browser.waitFor { userListPage.message.isDisplayed() }
        userListPage.header.logout()

        topPage = browser.at TopPage
        topPage.login userName, password

        browser.at DashBoardPage
    }

    public static String getBodyFromMail(String mailAddress , String mailPassword){
        String host = "imap.gmail.com";
        int port = 993;
        String user = mailAddress;
        String password = mailPassword;
        String target_folder = "INBOX";

        Properties props = System.getProperties();
        Session sess = Session.getInstance(props, null);
//		sess.setDebug(true);

        Store st = sess.getStore("imaps");
        st.connect(host, port, user, password);
        Folder fol = st.getFolder(target_folder);
        if(fol.exists()){
            fol.open(Folder.READ_ONLY);
            if (fol.getMessageCount() > 0) {
                for(Message m : fol.getMessages()){
                    return m.getContent()
                }
            } else {
                throw new RuntimeException("Folder is empty");
            }
            fol.close(false);
        }else{
            throw new FolderNotFoundException("Folder ${target_folder} does not exist.")
        }
        st.close();
    }
}