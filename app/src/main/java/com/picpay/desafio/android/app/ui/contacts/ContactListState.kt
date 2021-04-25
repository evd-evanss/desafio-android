package com.picpay.desafio.android.app.ui.contacts

import com.picpay.desafio.android.domain.model.local.UserContact

sealed class ContactListState {
    object DisplayError : ContactListState()
    data class LoadContacts(val contacts: List<UserContact>) : ContactListState()
    data class DisplayRefresh(val isRefresh: Boolean) : ContactListState()
}
