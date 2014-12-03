package org.star.helper

import javax.mail.*

/**
 * Created by kenichiro_ota on 14/11/29.
 */
class EmailHelper {
    String mailAddress
    String mailPassword

    public boolean containText(String text, int retryCount) {
        if (retryCount < 0) {
            return false
        } else {
            containText(text) ?: { sleep(10000); containText(text, retryCount - 1) }.call()
        }
    }

    public boolean containText(String text) {
        String host = "imap.gmail.com"
        int port = 993
        String targetFolder = "INBOX"

        Properties properties = System.getProperties()
        Session session = Session.getInstance(properties, null)

        Store store = session.getStore("imaps")
        store.connect(host, port, mailAddress, mailPassword)
        Folder folder = store.getFolder(targetFolder)

        boolean result = false
        if (folder.exists()) {
            folder.open(Folder.READ_ONLY)
            if (folder.getMessageCount() > 0) {
                Message[] messages = folder.getMessages().reverse().take(10)
                if (messages.find { Message message -> message.getContent().contains(text) }) {
                    result = true
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
