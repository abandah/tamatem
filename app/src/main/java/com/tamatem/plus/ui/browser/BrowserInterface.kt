package com.tamatem.plus.ui.browser

import android.view.View

open interface BrowserInterface {
    fun onClickBack(view :View)
    fun onClickForward(view :View)
    fun onClickRefresh(view :View)
    fun onClickClose(view :View)
}