package com.tamatem.plus.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.tamatem.plus.R

class CWebView : WebView {


    var listener: WebViewListener? = null

    constructor(context: Context) : super(context)

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    ) {

        init()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun init() {

        //change background color to black
        setBackgroundColor(context?.getColor(R.color.black) ?: 0)

        //enable javascript
        settings.javaScriptEnabled = true

        //enable dom storage
        settings.domStorageEnabled = true

        // enable third party cookies
        CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)

        // set webview chrome client
        webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {


                if (newProgress >= 100) {

                    listener?.onPageFinished(view, view?.url, canGoBack(), canGoForward())


                }
                if (newProgress >= 70) {
                    listener?.showLoader(false)
                } else {
                    listener?.showLoader(true)
                }
                super.onProgressChanged(view, newProgress)
            }

        }

        // set webview client
        webViewClient = object : WebViewClient() {
            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                listener?.onPageFinished(view, view?.url, canGoBack(), canGoForward())

            }


            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url ?: "")
                return false
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                listener?.showLoader(false)

            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                listener?.showLoader(true)
            }
        }
        // if you want to enable zoom feature

    }


    interface WebViewListener {
        fun onPageFinished(view: WebView?, url: String?, canGoBack: Boolean, canGoForward: Boolean)
        fun showLoader(show: Boolean)

    }

}