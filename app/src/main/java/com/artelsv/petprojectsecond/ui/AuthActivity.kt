package com.artelsv.petprojectsecond.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artelsv.petprojectsecond.databinding.ActivityMainBinding

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}