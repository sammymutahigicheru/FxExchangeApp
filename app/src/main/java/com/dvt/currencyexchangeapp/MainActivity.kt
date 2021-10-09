package com.dvt.currencyexchangeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dvt.currencyexchangeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}