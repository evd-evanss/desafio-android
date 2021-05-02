package com.picpay.desafio.android.app.ui.contacts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.verify
import com.picpay.desafio.android.rules.TestCoroutineRule
import com.picpay.desafio.android.data.repository.ContactsRepositoryTest
import com.picpay.desafio.android.domain.model.local.UserContact
import com.picpay.desafio.android.utils.extensions.startCollect
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ContactListViewModelTest {

    @get:Rule
    val coroutinesTestRule = TestCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var state: Observer<ContactListState>

    @Mock
    lateinit var fakeRepository: ContactsRepositoryTest

    lateinit var viewModel: ContactListViewModel

    @Before
    fun setup() {
        fakeRepository = ContactsRepositoryTest()
        viewModel = ContactListViewModel(fakeRepository)
        viewModel.state.observeForever(state)
    }

    @Test
    fun `GIVEN that my contact list is empty, WHEN the user opens the app, THEN I must enable loading, load contacts and disable loading`() {
        coroutinesTestRule.dispatcher.runBlockingTest {
            // Action
            viewModel.handle(ContactListIntent.GetContacts)

            // Assert
            fakeRepository.fetchContacts().startCollect(
                coroutineScope = this,
                onLoading = {
                    verify(state, atLeastOnce()).onChanged(ContactListState.DisplayLoading(it))
                },
                onSuccess = {
                    verify(state, atLeastOnce()).onChanged(ContactListState.LoadContacts(it))
                }
            )
        }
    }

    @Test
    fun `GIVEN that my contact list is not empty, WHEN user rotate the screen, THEN then I must load the list with locally saved data`() {
        coroutinesTestRule.dispatcher.runBlockingTest {
            // Arrange
            viewModel.localContactList.add(
                UserContact("", "Evandro", 3, "@evandro.costa")
            )
            // Action
            viewModel.handle(ContactListIntent.GetContacts)

            // Assert
            verify(state, atLeastOnce()).onChanged(
                ContactListState.LoadSavedContacts(contacts = viewModel.localContactList)
            )
        }
    }
}