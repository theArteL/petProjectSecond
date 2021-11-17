package com.artelsv.petprojectsecond.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.artelsv.petprojectsecond.R
import com.artelsv.petprojectsecond.databinding.ActivityMainBinding
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var appNavigator: AppNavigator

    @Inject
    lateinit var navHolder: NavigatorHolder

    @Inject
    lateinit var viewModel: MainViewModel

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setContentView(binding.root)

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
        viewModel.navigateBack()
    }

}