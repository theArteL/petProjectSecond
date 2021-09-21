package com.artelsv.petprojectsecond.ui

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.artelsv.petprojectsecond.App
import com.artelsv.petprojectsecond.R
import com.artelsv.petprojectsecond.databinding.ActivityMainBinding
import com.artelsv.petprojectsecond.ui.movie_list.MovieListFragment
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import dagger.android.AndroidInjection

class MainActivity : AppCompatActivity() {

    private lateinit var appNavigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appNavigator = AppNavigator(this, R.id.main_container)

        (application as App).getRouter().navigateTo(Screens.movieList())
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