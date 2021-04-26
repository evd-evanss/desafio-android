package com.picpay.desafio.android.data.source

import com.picpay.desafio.android.app.rules.TestCoroutineRule
import com.picpay.desafio.android.domain.model.local.UserContact
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

class FakeServiceTest {

    @get:Rule
    val coroutinesTestRule = TestCoroutineRule()

    private val service = FakeService()

    @Test
    fun fetchUsersSuccess() = coroutinesTestRule.testDispatcher.runBlockingTest {
        service.fetchUsersSuccess().collect {
            assertEquals(it, expectedUsers)
        }
    }

    @Test
    fun fetchUsersError() = coroutinesTestRule.testDispatcher.runBlockingTest {
        service.fetchUsersError()
            .catch {
                assertEquals(it.message, null)
            }
            .collect {
                assertEquals(it, expectedUsers)
            }
    }

    companion object {
        val expectedUsers = listOf(
            UserContact(
                image = "www.google.com",
                name = "Evandro",
                id = 0,
                username = "@evandro.costa"
            )
        )
    }
}