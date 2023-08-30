package com.tamatem.plus.ui.browser

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.tamatem.plus.R
import com.tamatem.plus.back.IOnBackPressed
import com.tamatem.plus.bases.BaseFragment
import com.tamatem.plus.databinding.FragmentBrowserBinding
import com.tamatem.plus.ui.CWebView


class BrowserFragment : BaseFragment(), CWebView.WebViewListener, IOnBackPressed {

    var viewModel: BrowserViewModel? = null
    var binding: FragmentBrowserBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[BrowserViewModel::class.java].apply {

        }

        binding = FragmentBrowserBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@BrowserFragment
            viewModel = this@BrowserFragment.viewModel
        }
        var callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.e("TAGTAG", "handleOnBackPressed: ${webView?.url}")
            }
        }
        requireActivity().getOnBackPressedDispatcher().addCallback(viewLifecycleOwner, callback);

        return binding!!.root
    }

    var webView: CWebView? = null
    var backButton : MenuItem? = null
    var forwardButton : MenuItem? = null
    var refreshButton : MenuItem? = null


    override fun onStart() {
        super.onStart()

        backButton = binding?.topAppBar?.menu?.findItem(R.id.back)
        forwardButton = binding?.topAppBar?.menu?.findItem(R.id.forward)
        refreshButton = binding?.topAppBar?.menu?.findItem(R.id.refresh)
        binding!!.topAppBar.setNavigationOnClickListener {
            it.findNavController().navigate(R.id.action_browserFragment_to_welcomeFragment)
        }

        binding!!.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                com.tamatem.plus.R.id.back -> {
                    webView?.goBack()
                    true
                }

                com.tamatem.plus.R.id.forward -> {
                    webView?.goForward()
                    true
                }

                com.tamatem.plus.R.id.refresh -> {
                    webView?.reload()
                    true
                }

                else -> false
            }
        }
        webView = binding?.webView
        webView?.listener = this
        webView?.loadUrl("https://tamatemplus.com")
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }



    override fun onPageFinished(
        view: WebView?,
        url: String?,
        canGoBack: Boolean,
        canGoForward: Boolean
    ) {
        backButton?.isEnabled = canGoBack?:false
        backButton?.icon?.alpha = if(canGoBack) 255 else 100
        forwardButton?.isEnabled = canGoForward?:false
        forwardButton?.icon?.alpha = if(canGoForward) 255 else 100

        if(webView?.url == "https://tamatemplus.com/" || webView?.url == "https://tamatemplus.com/home"){
            backButton?.isEnabled = false
            backButton?.icon?.alpha =100
            forwardButton?.isEnabled = false
            forwardButton?.icon?.alpha = 100
        }
    }

    override fun showLoader(show: Boolean) {
      //  viewModel?.visibilityOfLoading?.value = if (show) View.VISIBLE else View.GONE
    }

    override fun onBackPressed(): Boolean {
        webView?.goBack()
        return true
    }

}