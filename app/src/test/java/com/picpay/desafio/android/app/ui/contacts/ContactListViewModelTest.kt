package com.picpay.desafio.android.app.ui.contacts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.verify
import com.picpay.desafio.android.app.rules.TestCoroutineRule
import com.picpay.desafio.android.data.repository.ContactsRepositoryTest
import com.picpay.desafio.android.domain.model.local.UserContact
import com.picpay.desafio.android.utils.extensions.startCollect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

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
    fun `GIVEN that the user opens the application, WHEN for the first time, THEN I must enable loading, load contacts and disable loading`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // Action
            viewModel.handle(ContactListIntent.GetContacts)

            // Assert
            fakeRepository.fetchContacts().startCollect(
                coroutineScope = this,
                onLoading = {
                    verify(state, atLeastOnce()).onChanged(ContactListState.DisplayRefresh(it))
                },
                onSuccess = {
                    verify(state, atLeastOnce()).onChanged(ContactListState.LoadContacts(it))
                }
            )
        }
    }

    @Test
    fun `Given that I have the option to update the contact list, When I pull the screen down, THEN I must enable loading, load contacts and disable loading`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // Action
            viewModel.handle(ContactListIntent.RefreshContacts())

            // Assert
            fakeRepository.fetchContacts().startCollect(
                coroutineScope = this,
                onLoading = {
                    verify(state, atLeastOnce()).onChanged(ContactListState.DisplayRefresh(it))
                },
                onSuccess = {
                    verify(state, atLeastOnce()).onChanged(ContactListState.LoadContacts(it))
                }
            )
        }
    }

    @Test
    fun `GIVEN given that screen rotation is enabled, WHEN user rotate the screen, THEN then I must load the list with locally saved data`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // Arrange
            viewModel.contactList.add(
                UserContact("", "Evandro", 3, "@evandro.costa")
            )
            // Action
            viewModel.handle(ContactListIntent.GetContacts)

            // Assert
            verify(state, atLeastOnce()).onChanged(
                ContactListState.LoadSavedContacts(contacts = viewModel.contactList)
            )
        }
    }
}