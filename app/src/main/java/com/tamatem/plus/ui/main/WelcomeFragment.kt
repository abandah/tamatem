package com.tamatem.plus.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.tamatem.plus.R
import com.tamatem.plus.bases.BaseFragment
import com.tamatem.plus.databinding.FragmentWelcomeBinding

class WelcomeFragment : BaseFragment(), WelcomeInterface  {

    private var viewModel: WelcomeViewModel? = null
    private var binding : FragmentWelcomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[WelcomeViewModel::class.java].apply {
            mainActivityInterface = this@WelcomeFragment
        }
        binding = FragmentWelcomeBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@WelcomeFragment

            viewModel = this@WelcomeFragment.viewModel
        }

        return binding!!.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun openBrowser(view: View) {
        // go to browser fragment
        view.findNavController().navigate(R.id.action_welcomeFragment_to_browserFragment)

    }


}