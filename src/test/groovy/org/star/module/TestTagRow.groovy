package org.star.module

import geb.Module

/**
 * Created by yoshimura on 2014/12/03.
 */
class TestTagRow  extends Module{
    static content = {
        cell { $("td", it) }
        name { cell(0).text() }
        description { cell(1).text() }
        edit { cell(2).$("a", 0) }
        delete { cell(2)$("a", 1) }
    }
}
