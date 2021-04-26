package com.picpay.desafio.android.app.ui.contacts

import android.util.Log
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

    internal val contactList = mutableListOf<UserContact>()

    override fun handle(intent: ContactListIntent) {
        when (intent) {
            is ContactListIntent.GetContacts -> getContacts()
            is ContactListIntent.RefreshContacts -> getContacts(isRefresh = intent.isRefresh)
        }
    }

    private fun getContacts(isRefresh: Boolean = false) = viewModelScope.run {
        when {
            isRefresh -> fetchContactsInApi()
            contactList.isEmpty() -> fetchContactsInApi()
            else -> _state.value = ContactListState.LoadSavedContacts(contacts = contactList)
        }
    }

    private fun fetchContactsInApi() = repository.fetchContacts().startCollect(
        coroutineScope = viewModelScope,
        onLoading = { isLoading ->
            _state.value = ContactListState.DisplayRefresh(isRefresh = isLoading)
        },
        onSuccess = { contacts ->
            contactList.clear()
            contactList.addAll(contacts.toMutableList())
            _state.value = ContactListState.LoadContacts(contacts = contacts)
        },
        onError = {
            _state.value = ContactListState.DisplayError
        }
    )
}