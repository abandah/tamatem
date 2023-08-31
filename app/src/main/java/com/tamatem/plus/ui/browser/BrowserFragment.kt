package com.tamatem.plus.ui.browser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.tamatem.plus.R
import com.tamatem.plus.back.IOnBackPressed
import com.tamatem.plus.bases.BaseFragment
import com.tamatem.plus.databinding.FragmentBrowserBinding
import com.tamatem.plus.ui.CWebView


class BrowserFragment : BaseFragment(), CWebView.WebViewListener, IOnBackPressed {

    var viewModel: BrowserViewModel? = null
    private var binding: FragmentBrowserBinding? = null

    private var webView: CWebView? = null
    private var backButton : MenuItem? = null
    private var forwardButton : MenuItem? = null
    private var refreshButton : MenuItem? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[BrowserViewModel::class.java]
        binding = FragmentBrowserBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@BrowserFragment
            viewModel = this@BrowserFragment.viewModel
        }
        return binding!!.root
    }



    override fun onStart() {
        super.onStart()

        initViews()


        // run webview
        webView?.loadUrl("https://tamatemplus.com")
    }

    private fun initViews() {
        //init views
        backButton = binding?.topAppBar?.menu?.findItem(R.id.back)
        forwardButton = binding?.topAppBar?.menu?.findItem(R.id.forward)
        refreshButton = binding?.topAppBar?.menu?.findItem(R.id.refresh)

        // init webview and set listener
        webView = binding?.webView
        webView?.listener = this


        // onClick navigation icon
        binding!!.topAppBar.setNavigationOnClickListener {
            it.findNavController().navigate(R.id.action_browserFragment_to_welcomeFragment)
        }


        // onClick menu items
        binding!!.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.back -> {
                    webView?.goBack()
                    true
                }

               R.id.forward -> {
                    webView?.goForward()
                    true
                }

                R.id.refresh -> {
                    webView?.reload()
                    true
                }

                else -> false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }



    // to handle back and forward buttons in webview
    override fun onPageFinished(
        view: WebView?,
        url: String?,
        canGoBack: Boolean,
        canGoForward: Boolean
    ) {
        backButton?.isEnabled = canGoBack
        backButton?.icon?.alpha = if(canGoBack) 255 else 100
        forwardButton?.isEnabled = canGoForward
        forwardButton?.icon?.alpha = if(canGoForward) 255 else 100


        // in home page disable back and forward buttons
        if(webView?.url == "https://tamatemplus.com/" || webView?.url == "https://tamatemplus.com/home"){
            backButton?.isEnabled = false
            backButton?.icon?.alpha =100
            forwardButton?.isEnabled = false
            forwardButton?.icon?.alpha = 100
        }
    }

    override fun showLoader(show: Boolean) {
        viewModel?.visibilityOfLoading?.value = if (show) View.VISIBLE else View.GONE
    }

    override fun onBackPressed(): Boolean {
        webView?.goBack()
        return true
    }

}