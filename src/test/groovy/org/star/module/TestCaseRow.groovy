package org.star.module

import geb.Module

/**
 * Created by itagakishintarou on 2014/11/24.
 */
class TestCaseRow extends Module {
    static content = {
        cell { $("td", it) }
        name { cell(0).text() }
        scenario { cell(1).text() }
        tags { cell(2).text() }
    }
}
