package com.tamatem.plus.ui.main

import android.view.View

class WelcomeViewModel : com.tamatem.plus.bases.BaseViewModel(), WelcomeInterface {

    internal var mainActivityInterface: WelcomeInterface? = null

    override fun openBrowser(view: View) {
        mainActivityInterface?.openBrowser(view)

    }


}