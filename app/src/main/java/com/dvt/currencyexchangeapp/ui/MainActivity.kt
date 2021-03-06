package com.dvt.currencyexchangeapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dvt.core.helpers.changeActivityStatusBarColor
import com.dvt.currencyexchangeapp.R
import com.dvt.currencyexchangeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        changeActivityStatusBarColor(this)

        val navHostFrag =
            supportFragmentManager.findFragmentById(R.id.home_nav_host_fragment) as NavHostFragment
        val navController = navHostFrag.navController

        binding.bottomNavigationView.setupWithNavController(navController)

    }
}