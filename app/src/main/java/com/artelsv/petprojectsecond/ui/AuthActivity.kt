package com.artelsv.petprojectsecond.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artelsv.petprojectsecond.R
import com.artelsv.petprojectsecond.databinding.ActivityAuthBinding
import com.github.terrakok.cicerone.androidx.AppNavigator

class AuthActivity : AppCompatActivity() {

    private lateinit var appNavigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appNavigator = AppNavigator(this, R.id.container)

        binding.btnLoginGuest.setOnClickListener {
//            (application as App).getRouter().replaceScreen(ActivityScreen { Intent(this, MainActivity::class.java) })
        }
    }

//    override fun onStart() {
//        super.onStart()
//        (application as App).cicerone.getNavigatorHolder().setNavigator(appNavigator)
//    }
//
//    override fun onPause() {
//        super.onPause()
//        (application as App).cicerone.getNavigatorHolder().removeNavigator()
//    }
}