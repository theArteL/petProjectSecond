package com.artelsv.petprojectsecond

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.util.HumanReadables
import androidx.test.espresso.util.TreeIterables
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.StringDescription
import java.util.concurrent.TimeoutException

fun hasItemCount(matcher: Matcher<Int>): Matcher<View> {
    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has item count: ")
            matcher.describeTo(description)
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            return matcher.matches(view.adapter!!.itemCount)
        }
    }
}

fun waitUntil(matcher: Matcher<View>): ViewAction {
    return ViewActions.actionWithAssertions(object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return ViewMatchers.isAssignableFrom(View::class.java)
        }

        override fun getDescription(): String {
            val description = StringDescription()
            matcher.describeTo(description)
            return String.format("wait until: %s", description)
        }

        override fun perform(uiController: UiController, view: View) {
            if (!matcher.matches(view)) {
                val callback = LayoutChangeCallback(matcher)
                try {
                    IdlingRegistry.getInstance().register(callback)
                    view.addOnLayoutChangeListener(callback)
                    uiController.loopMainThreadUntilIdle()
                } finally {
                    view.removeOnLayoutChangeListener(callback)
                    IdlingRegistry.getInstance().unregister(callback)
                }
            }
        }
    })
}

private class LayoutChangeCallback(private val matcher: Matcher<View>) :
    IdlingResource, View.OnLayoutChangeListener {
    private var callback: IdlingResource.ResourceCallback? = null
    private var matched = false
    override fun getName(): String {
        return "Layout change callback"
    }

    override fun isIdleNow(): Boolean {
        return matched
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        this.callback = callback
    }

    override fun onLayoutChange(
        v: View,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int,
        oldLeft: Int,
        oldTop: Int,
        oldRight: Int,
        oldBottom: Int
    ) {
        matched = matcher.matches(v)
        callback!!.onTransitionToIdle()
    }
}

fun waitId(viewId: Int, millis: Long): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return ViewMatchers.isRoot()
        }

        override fun getDescription(): String {
            return "wait for a specific view with id <$viewId> during $millis millis."
        }

        override fun perform(uiController: UiController, view: View) {
            uiController.loopMainThreadUntilIdle()
            val startTime = System.currentTimeMillis()
            val endTime = startTime + millis
            val viewMatcher = ViewMatchers.withId(viewId)
            do {
                for (child in TreeIterables.breadthFirstViewTraversal(view)) {
                    // found view with required ID
                    if (viewMatcher.matches(child)) {
                        return
                    }
                }
                uiController.loopMainThreadForAtLeast(50)
            } while (System.currentTimeMillis() < endTime)
            throw PerformException.Builder()
                .withActionDescription(this.description)
                .withViewDescription(HumanReadables.describe(view))
                .withCause(TimeoutException())
                .build()
        }
    }
}