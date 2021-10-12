package com.artelsv.petprojectsecond.ui.moviedetail

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.artelsv.petprojectsecond.R
import com.artelsv.petprojectsecond.hasItemCount
import com.artelsv.petprojectsecond.ui.MainActivity
import com.artelsv.petprojectsecond.ui.movielist.MovieAdapter
import com.artelsv.petprojectsecond.waitUntil
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matchers.greaterThan
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MovieListToMovieDetailFragmentBackTest : TestCase() {

    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun navigationTest_ClickOnFirstItem_MovieListToMovieDetailAndBack() {
        onView(withId(R.id.rv_movies_now_playing))
            .perform(
                waitUntil(hasItemCount(greaterThan(0))),
                actionOnItemAtPosition<MovieAdapter.ViewHolder>(0, click()))

        onView(withId(R.id.fl_main_container))
            .check(matches(isDisplayed()))
            .perform(pressBack())

        onView(withId(R.id.fl_movie_list_main_container))
            .check(matches(isDisplayed()))
    }

    @Test
    fun navigationTest_ClickOnSecondItem_MovieListToMovieDetailAndBack() {
        onView(withId(R.id.rv_movies_now_playing))
            .perform(
                waitUntil(hasItemCount(greaterThan(0))),
                actionOnItemAtPosition<MovieAdapter.ViewHolder>(1, click()))

        onView(withId(R.id.fl_main_container))
            .check(matches(isDisplayed()))
            .perform(pressBack())

        onView(withId(R.id.fl_movie_list_main_container))
            .check(matches(isDisplayed()))
    }

    @Test
    fun navigationTest_ClickOnNegativeItem_MovieListToMovieDetailAndBackError() {
        try {
            onView(withId(R.id.rv_movies_now_playing))
                .perform(
                    waitUntil(hasItemCount(greaterThan(0))),
                    actionOnItemAtPosition<MovieAdapter.ViewHolder>(-1, click()))

            onView(withId(R.id.fl_main_container))
                .check(matches(isDisplayed()))
                .perform(pressBack())

            onView(withId(R.id.fl_movie_list_main_container))
                .check(matches(isDisplayed()))
        } catch (e: PerformException) {
            assert(true)
        }

    }
}