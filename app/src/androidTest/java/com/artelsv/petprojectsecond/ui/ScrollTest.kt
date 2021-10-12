package com.artelsv.petprojectsecond.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.artelsv.petprojectsecond.R
import com.artelsv.petprojectsecond.hasItemCount
import com.artelsv.petprojectsecond.ui.movielist.MovieAdapter
import com.artelsv.petprojectsecond.waitUntil
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScrollTest {

    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun scrollToBottomClickItem_goToMovieDetailAndGoBack() {
        Espresso.onView(ViewMatchers.withId(R.id.rv_movies_now_playing))
            .perform(
                waitUntil(hasItemCount(Matchers.greaterThan(10))),
                RecyclerViewActions.scrollToPosition<MovieAdapter.ViewHolder>(5)
            )

        Espresso.onView(ViewMatchers.withId(R.id.rv_movies_now_playing))
            .perform(RecyclerViewActions.actionOnItemAtPosition<MovieAdapter.ViewHolder>(0, ViewActions.click()))

        Espresso.onView(ViewMatchers.withId(R.id.fl_main_container))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed())).perform(ViewActions.pressBack())

        Espresso.onView(ViewMatchers.withId(R.id.fl_movie_list_main_container))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}