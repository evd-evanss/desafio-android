package com.picpay.desafio.android.app.ui.contacts

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.picpay.desafio.android.R
import com.picpay.desafio.android.app.utils.getActivity
import com.picpay.desafio.android.app.utils.matchers.RecyclerViewMatchers.atPosition
import com.picpay.desafio.android.app.utils.matchers.RecyclerViewMatchers.withViewAtPosition
import com.picpay.desafio.android.app.utils.matchers.ViewMatchers
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test

class ContactListActivityTest {

    @get:Rule
    var rule = ActivityScenarioRule(ContactListActivity::class.java)

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun shouldShowTitle_whenActivityIsLaunched_Success() {
        launchActivity<ContactListActivity>().apply {
            val expectedTitle = context.getString(R.string.title)

            moveToState(Lifecycle.State.RESUMED)

            onView(withText(expectedTitle)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun shouldShowTitle_whenActivityIsLaunched_Error() {
        launchActivity<ContactListActivity>().apply {
            val expectedTitle = context.getString(R.string.title)

            moveToState(Lifecycle.State.RESUMED)

            onView(withText(expectedTitle)).check(matches(not(isDisplayed())))
        }
    }

    @Test
    fun shouldShowRefresh_whenSwipeScreenForDown_Success() {
        rule.scenario.apply {
            moveToState(Lifecycle.State.RESUMED)
            onView(withId(R.id.swipeRefresh))
                .perform(ViewMatchers().withCustomConstraints(swipeDown(), isDisplayingAtLeast(85)))
                .check(matches(isDisplayed()))
        }
    }

    @Test
    fun shouldShowRefresh_whenSwipeScreenForDown_Error() {
        rule.scenario.apply {
            moveToState(Lifecycle.State.RESUMED)
            onView(withId(R.id.swipeRefresh))
                .perform(ViewMatchers().withCustomConstraints(swipeDown(), isDisplayingAtLeast(85)))
                .check(matches(not(isDisplayed())))
        }
    }

    @Test
    fun shouldDisplayLastItem_whenScrollScreenForLastPosition_Sucess() {
        val activity = getActivity(rule)
        val nameInLastPosition = activity.adapter.contacts.last().name
        val lastPosition = activity.adapter.contacts.size - 1

        rule.scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.recyclerView))
            .perform(scrollToPosition<ContactListAdapter.UserListItemViewHolder>(lastPosition))
            .check(
                matches(
                    atPosition(
                        lastPosition,
                        hasDescendant(withText(nameInLastPosition))
                    )
                )
            )
    }

    @Test
    fun shouldDisplayLastItem_whenScrollScreenForLastPosition_Error() {
        val nameInLastPosition = "Evandro"
        val lastPosition = getActivity(rule).adapter.contacts.size - 1

        onView(withId(R.id.recyclerView))
            .perform(scrollToPosition<ContactListAdapter.UserListItemViewHolder>(lastPosition))
            .check(matches(atPosition(lastPosition, hasDescendant(withText(nameInLastPosition)))))
    }

    @Test
    fun shouldDisplayTheContactImage_whenScrollScreenInAllPositions_Success() {
        repeat(getActivity(rule).adapter.contacts.size - 1) { position ->
            onView(withId(R.id.recyclerView))
                .perform(scrollToPosition<ContactListAdapter.UserListItemViewHolder>(position))
                .check(
                    matches(
                        withViewAtPosition(
                            position = position,
                            itemMatcher = hasDescendant(
                                allOf(
                                    withId(R.id.picture),
                                    isDisplayed()
                                )
                            )
                        )
                    )
                )
        }
    }

    @Test
    fun shouldDisplayTheContactImage_whenScrollScreenInAllPositions_Error() {
        repeat(getActivity(rule).adapter.contacts.size - 1) { position ->
            onView(withId(R.id.recyclerView))
                .perform(scrollToPosition<ContactListAdapter.UserListItemViewHolder>(position))
                .check(
                    matches(
                        withViewAtPosition(
                            position,
                            hasDescendant(
                                allOf(
                                    withId(R.id.picture),
                                    not(isDisplayed())
                                )
                            )
                        )
                    )
                )
        }
    }
}