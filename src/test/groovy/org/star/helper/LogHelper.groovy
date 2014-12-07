package org.star.helper

import java.util.regex.Matcher

/**
 * Created by yoshimura on 2014/12/07.
 */
class LogHelper {
    static String unixShell = "heroku.bat logs --app fathomless-stream-3131"
    static String windowsCmd = "cmd /c  heroku.bat logs --app fathomless-stream-3131"

    public static String getLogText(){
        if(System.properties['os.name'].toString().toLowerCase().contains('windows')){
            return windowsCmd.execute().text
        } else {
            Process process = unixShell.execute()
            process.waitFor()
            return process.text
        }
    }

    public static String getTagId(){
        String result = getLogText()
        Matcher tagIdMatcher = (result =~ /(?s)aop.TagServiceGet.*? : (\d+)/)
        return tagIdMatcher[(int) tagIdMatcher.size() - 1][1]
    }
}
