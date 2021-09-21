package com.artelsv.petprojectsecond.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artelsv.petprojectsecond.App
import com.artelsv.petprojectsecond.R
import com.artelsv.petprojectsecond.databinding.ActivityAuthBinding
import com.artelsv.petprojectsecond.databinding.ActivityMainBinding
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.AppNavigator
import dagger.android.AndroidInjection
import okhttp3.Route
import javax.inject.Inject

class AuthActivity : AppCompatActivity() {

    private lateinit var appNavigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appNavigator = AppNavigator(this, R.id.container)

        binding.btnLoginGuest.setOnClickListener {
            (application as App).getRouter().replaceScreen(ActivityScreen { Intent(this, MainActivity::class.java) })
        }
    }

    override fun onStart() {
        super.onStart()
        (application as App).cicerone.getNavigatorHolder().setNavigator(appNavigator)
    }

    override fun onPause() {
        super.onPause()
        (application as App).cicerone.getNavigatorHolder().removeNavigator()
    }
}