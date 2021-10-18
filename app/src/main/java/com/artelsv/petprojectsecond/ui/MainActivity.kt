package com.artelsv.petprojectsecond.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artelsv.petprojectsecond.R
import com.artelsv.petprojectsecond.databinding.ActivityMainBinding
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var appNavigator: AppNavigator

    @Inject
    lateinit var navHolder: NavigatorHolder

    @Inject
    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        appNavigator = AppNavigator(this, R.id.main_container)
    }

    override fun onStart() {
        super.onStart()
        navHolder.setNavigator(appNavigator)
    }

    override fun onPause() {
        super.onPause()
        navHolder.removeNavigator()
    }

    override fun onBackPressed() {
        appNavigator.applyCommands(arrayOf(Back()))
    }

}