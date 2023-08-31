package com.tamatem.plus.ui

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.tamatem.plus.R

class CWebView : WebView {


    public var listener: WebViewListener? = null

    constructor(context: Context) : super(context)

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    ) {

        init()
    }

    private fun init() {
        setBackgroundColor(context?.getColor(R.color.black) ?: 0)
        settings.javaScriptEnabled = true;
        settings.loadsImagesAutomatically = true;
        settings.databaseEnabled = true;
        settings.domStorageEnabled = true;
        settings.setGeolocationEnabled(true);
        settings.javaScriptCanOpenWindowsAutomatically = true;
        isHorizontalScrollBarEnabled = false;
        settings.allowFileAccess = true;
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;
        scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY;
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;
        settings.pluginState = WebSettings.PluginState.ON;
        settings.mediaPlaybackRequiresUserGesture = false;
        settings.mixedContentMode = 0;
        setLayerType(View.LAYER_TYPE_HARDWARE, null);

        webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {

                Log.e("TAGTAG", "onProgressChanged: $newProgress")

                if (newProgress >= 100) {

                    listener?.onPageFinished(view, view?.url, canGoBack(), canGoForward())


                }
                if(newProgress >= 70){
                    listener?.showLoader(false)
                } else{
                    listener?.showLoader(true)
                }
                super.onProgressChanged(view, newProgress)
            }

        }

        webViewClient = object : WebViewClient() {
            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                listener?.onPageFinished(view, view?.url, canGoBack(), canGoForward())

            }


            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                Log.e("TAGTAG", "shouldOverrideUrlLoading: " + url)
                view?.loadUrl(url ?: "")
                return false
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.e("TAGTAG", "onPageFinished: " + url)
                listener?.showLoader(false)

            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                listener?.showLoader(true)
            }
        }
        // if you want to enable zoom feature
        settings.setSupportZoom(true)
    }


    interface WebViewListener {
        fun onPageFinished(view: WebView?, url: String?, canGoBack: Boolean, canGoForward: Boolean)
        fun showLoader( show : Boolean)

    }

}