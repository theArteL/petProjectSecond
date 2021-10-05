package com.artelsv.petprojectsecond.ui

import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.artelsv.petprojectsecond.R
import junit.framework.TestCase
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest : TestCase() {

    @Rule
    fun activityScenarioRule(): ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun checkContainerIsDisplayed() {
//        Espresso.onView(ViewMatchers.withId(R.id.container)).check()
    }

    @Test
    fun getNavHolder() {
    }

    @Test
    fun setNavHolder() {
    }

    @Test
    fun getRouter() {
    }

    @Test
    fun setRouter() {
    }

    @Test
    fun onCreate() {
    }

    @Test
    fun onStart() {
    }

    @Test
    fun onPause() {
    }

    @Test
    fun onBackPressed() {
    }
}