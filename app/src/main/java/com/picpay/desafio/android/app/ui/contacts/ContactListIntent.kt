package com.picpay.desafio.android.app.ui.contacts

sealed class ContactListIntent {
    data class RefreshContacts(val isRefresh: Boolean = true) : ContactListIntent()
    object GetContacts : ContactListIntent()
}
