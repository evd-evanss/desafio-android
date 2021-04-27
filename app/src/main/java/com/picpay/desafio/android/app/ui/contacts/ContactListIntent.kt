package com.picpay.desafio.android.app.ui.contacts

sealed class ContactListIntent {
    object GetContacts : ContactListIntent()
}
