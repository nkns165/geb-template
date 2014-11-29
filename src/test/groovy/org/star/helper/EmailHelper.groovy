package org.star.helper

import javax.mail.Folder
import javax.mail.FolderNotFoundException
import javax.mail.Message
import javax.mail.Session
import javax.mail.Store

/**
 * Created by kenichiro_ota on 14/11/29.
 */
class EmailHelper {
    String mailAddress
    String mailPassword

    public boolean containText(String text) {
        String host = "imap.gmail.com"
        int port = 993
        String targetFolder = "INBOX"

        Properties properties = System.getProperties()
        Session session = Session.getInstance(properties, null)

        Store store = session.getStore("imaps")
        store.connect(host, port, mailAddress, mailPassword)
        Folder folder = store.getFolder(targetFolder)

        sleep(10000) // TODO 要リファクタ タイムアウトとか設定出来るようにする
        boolean result = false
        if (folder.exists()) {
            folder.open(Folder.READ_ONLY)
            if (folder.getMessageCount() > 0) {
                int limit = 1
                for (Message message : folder.getMessages().reverse()) {
                    String content = (String) message.getContent(); // 手抜き
                    if (content.contains(text)) {
                        result = true
                        break
                    }
                    limit++
                    if (limit > 10) { // 最新から10個で打ち切る
                        break
                    }
                }
            }
            folder.close(false)
        } else {
            throw new FolderNotFoundException(folder, "Folder ${targetFolder} does not exist.")
        }
        store.close()
        return result
    }
}
