package com.tamatem.plus.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.tamatem.plus.back.IOnBackPressed
import com.tamatem.plus.bases.BaseActivity
import com.tamatem.plus.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private var doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


    // implement on back pressed to handle back button in fragments
    @Deprecated("Deprecated in Java")
    @Suppress("DEPRECATION")
    override fun onBackPressed() {

        val navHostFragment = supportFragmentManager.fragments.first() as? NavHostFragment
        if (navHostFragment != null) {
            val childFragments = navHostFragment.childFragmentManager.fragments
            childFragments.forEach { fragment ->
                if (fragment is IOnBackPressed) {
                    if (fragment.onBackPressed()) {
                        return
                    }
                }else{
                    if (doubleBackToExitPressedOnce) {
                        super.onBackPressed()
                        return
                    }

                    this.doubleBackToExitPressedOnce = true
                    Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

                    Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
                }
            }
        }


    }
}