package com.picpay.desafio.android.app.ui.contacts

import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.app.base.BaseViewModel
import com.picpay.desafio.android.data.repository.ContactsRepository
import com.picpay.desafio.android.domain.model.local.UserContact
import com.picpay.desafio.android.utils.extensions.startCollect
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val repository: ContactsRepository
) : BaseViewModel<ContactListIntent, ContactListState>() {

    internal val localContactList = mutableListOf<UserContact>()

    override fun handle(intent: ContactListIntent) {
        when (intent) {
            is ContactListIntent.GetContacts -> getContacts()
        }
    }

    private fun getContacts() = viewModelScope.run {
        if (localContactList.isEmpty()) {
            fetchContactsInApi()
        } else {
            _state.value = ContactListState.LoadSavedContacts(contacts = localContactList)
        }
    }

    private fun fetchContactsInApi() = repository.fetchContacts().startCollect(
        coroutineScope = viewModelScope,
        onLoading = { isLoading ->
            _state.value = ContactListState.DisplayLoading(isLoading = isLoading)
        },
        onSuccess = { contacts ->
            localContactList.clear()
            localContactList.addAll(contacts.toMutableList())
            _state.value = ContactListState.LoadContacts(contacts = contacts)
        },
        onError = {
            _state.value = ContactListState.DisplayError
        }
    )
}