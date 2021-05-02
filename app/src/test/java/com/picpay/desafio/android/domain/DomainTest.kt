package com.picpay.desafio.android.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.picpay.desafio.android.data.source.ContactDataSourceFake
import com.picpay.desafio.android.domain.mapper.ContactsMapperFake
import com.picpay.desafio.android.rules.TestCoroutineRule
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DomainTest {

    @get:Rule
    val rule = TestCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mapperFake: ContactsMapperFake

    private lateinit var service: ContactDataSourceFake

    @Before
    fun setup() {
        mapperFake = ContactsMapperFake()
        service = ContactDataSourceFake()
    }

    @Test
    fun shouldConverterResponseToModel_Success() = runBlockingTest {
        mapperFake.responseToModel(service.fetchUsersSuccess())
            .onEach { model ->
                model.forEach {
                    assertEquals("www.google.com", it.image)
                    assertEquals("Evandro", it.name)
                    assertEquals("@evandro.costa", it.username)
                    assertEquals(0, it.id)
                }
            }
            .stateIn(this)
    }

    @Test
    fun shouldConverterResponseToModel_WithNullSafe() = runBlockingTest {
        mapperFake.responseToModel(service.fetchUsersWithFieldsNull())
            .onEach { model ->
                model.forEach {
                    assertEquals("", it.image)
                    assertEquals("Evandro", it.name)
                    assertEquals("", it.username)
                    assertEquals(2, it.id)
                }
            }
            .stateIn(this)
    }
}