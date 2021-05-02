package com.picpay.desafio.android.data

import com.picpay.desafio.android.rules.TestCoroutineRule
import com.picpay.desafio.android.data.repository.ContactsRepositoryImplFake
import com.picpay.desafio.android.data.source.ContactDataSourceFake
import com.picpay.desafio.android.domain.local.UserContactFake
import com.picpay.desafio.android.domain.mapper.ContactsMapperFake
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

class DataTest {

    @get:Rule
    val coroutinesTestRule = TestCoroutineRule()

    private val dataSource = ContactDataSourceFake()
    private val mapper = ContactsMapperFake()
    private val repository = ContactsRepositoryImplFake(
        dataSource = dataSource,
        mapper = mapper
    )

    @Test
    fun shouldFetchContactsWith_Success() = runBlockingTest {
        repository.fetchContactsSuccess().collect {
            assertEquals(expectedUsers, it)
        }
    }

    @Test
    fun shouldFetchContactsWith_NullSafe() = runBlockingTest {
        repository.fetchContactsSuccessWithNullSafe().collect {
            assertEquals(expectedUsersNullSafe, it)
        }
    }

    @Test
    fun shouldFetchContactsWith_Error() = runBlockingTest {
        repository.fetchContactsError().catch {
            assertEquals(expectedMessage, it.message)
        }.launchIn(this)
    }

    companion object {
        val expectedUsers = listOf(
            UserContactFake(
                image = "www.google.com",
                name = "Evandro",
                id = 0,
                username = "@evandro.costa"
            )
        )
        val expectedUsersNullSafe = listOf(
            UserContactFake(
                image = "",
                name = "Evandro",
                id = 2,
                username = ""
            )
        )
        val expectedMessage = "Falha ao buscar lista de contados"
    }
}